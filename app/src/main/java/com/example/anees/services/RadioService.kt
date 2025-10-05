package com.example.anees.services

import android.app.Notification
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.media.AudioManager
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.support.v4.media.session.MediaSessionCompat
import androidx.media3.common.Player
import com.example.anees.R
import com.example.anees.data.model.audio.AudioTrack
import com.example.anees.data.model.radio.RadioStations
import com.example.anees.utils.media_helper.AudioFocusHelper
import com.example.anees.utils.media_helper.RadioNotificationManager
import com.example.anees.utils.media_helper.RadioPlayer
import com.example.anees.utils.sura_mp3_helper.suraUrls
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RadioService : Service() {

    companion object {
        const val ACTION_PLAY = "action_play"
        const val ACTION_PAUSE = "action_pause"
        const val ACTION_NEXT = "action_next"
        const val ACTION_PREV = "action_prev"

        const val ACTION_SKIP_FORWARD = "action_skip_forward"

        const val ACTION_SKIP_BACKWARD = "action_skip_backward"

        const val ACTION_CLOSE = "action_close"
        const val ACTION_PLAYBACK_STATE = "com.example.anees.ACTION_PLAYBACK_STATE"
        const val ACTION_STATION_CHANGED = "com.example.anees.ACTION_STATION_CHANGED"
        const val EXTRA_IS_PLAYING = "extra_is_playing"

        const val EXTRA_STATION_INDEX = "extra_station_index"

        const val ACTION_UPDATE_PROGRESS = "com.example.anees.ACTION_UPDATE_PROGRESS"
        const val EXTRA_CURRENT_POSITION = "extra_current_position"
        const val EXTRA_DURATION = "extra_duration"
    }

    private var progressUpdateRunnable: Runnable? = null
    private val handler = Handler(Looper.getMainLooper())
    private lateinit var audioManager: AudioManager
    private lateinit var mediaSession: MediaSessionCompat
    private lateinit var notificationManager: RadioNotificationManager
    private lateinit var audioFocusHelper: AudioFocusHelper

    private val stations = RadioStations.stations
    private var isSura = false;
    private var reciterName = ""
    private lateinit var reciterUrl: String
    private var currentIndex = 0

    var audio: AudioTrack? = null

    override fun onCreate() {
        super.onCreate()
        RadioPlayer.initializePlayer(this)

        audioManager = getSystemService(AUDIO_SERVICE) as AudioManager
        mediaSession = MediaSessionCompat(this, "AneesRadioSession").apply {
            isActive = true
        }
        notificationManager = RadioNotificationManager(this, mediaSession)
        audioFocusHelper = AudioFocusHelper(
            context = this,
            onFocusGain = {
                RadioPlayer.play()
                sendPlaybackStateBroadcast(true)
            },
            onFocusLoss = {
                RadioPlayer.pause()
                sendPlaybackStateBroadcast(false)
            } ,
            onDuck = {
                RadioPlayer.getPlayer()?.volume = 0.3f
            } ,
        )

        RadioPlayer.setListener(object : Player.Listener {
            override fun onIsPlayingChanged(isPlaying: Boolean) {
                updateNotification()
            }

            override fun onPlaybackStateChanged(playbackState: Int) {
                if (playbackState == Player.STATE_READY && isSura) {
                    startProgressUpdates()
                } else if (playbackState == Player.STATE_ENDED || playbackState == Player.STATE_IDLE) {
                    stopProgressUpdates()
                }
            }
        })
    }

    private fun startProgressUpdates() {
        stopProgressUpdates()

        progressUpdateRunnable = object : Runnable {
            override fun run() {
                if (isSura && RadioPlayer.isPlaying()) {
                    val player = RadioPlayer.getPlayer()
                    player?.let {
                        val currentPosition = it.currentPosition
                        val duration = it.duration

                        val intent = Intent(ACTION_UPDATE_PROGRESS).apply {
                            setPackage(packageName)
                            putExtra(EXTRA_CURRENT_POSITION, currentPosition)
                            putExtra(EXTRA_DURATION, duration)
                        }
                        sendBroadcast(intent)

                        updateNotification()
                    }

                    // Schedule next update
                    handler.postDelayed(this, 1000) // Update every second
                }
            }
        }

        handler.post(progressUpdateRunnable!!)
    }

    private fun stopProgressUpdates() {
        progressUpdateRunnable?.let {
            handler.removeCallbacks(it)
            progressUpdateRunnable = null
        }
    }
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_CLOSE -> {
                stopSelf()
                return START_NOT_STICKY
            }
            ACTION_PLAY -> {
                if (audioFocusHelper.requestAudioFocus()) {
                    RadioPlayer.play()
                    sendPlaybackStateBroadcast(true)
                }
            }
            ACTION_PAUSE -> {
                RadioPlayer.pause()
                sendPlaybackStateBroadcast(false);
            }
            ACTION_NEXT -> {
                playNext()
                sendStationChangedBroadcast(currentIndex)
            }
            ACTION_PREV -> {
                playPrev()
                sendStationChangedBroadcast(currentIndex)
            }
            ACTION_SKIP_FORWARD -> {
                SkipForward()
            }
            ACTION_SKIP_BACKWARD -> {
                SkipBackward()
            }
            else -> {
                audio = intent?.getParcelableExtra("audio")
                val isRadio = intent?.getBooleanExtra("isRadio", true) ?: true

                audio.let {
                    startPlayback(audio?.uri!!)
                    sendPlaybackStateBroadcast(true)
                    currentIndex = if (isRadio) {
                        stations.indexOfFirst { station -> station.url == audio?.uri  }
                    }else {
                        reciterUrl = audio?.reciter!!
                        reciterName = audio?.reciter!!
                        isSura = true
                        audio?.index!!
                    }
                    sendStationChangedBroadcast(currentIndex)
                }
            }
        }

        startForeground(RadioNotificationManager.NOTIFICATION_ID, buildNotification())
        return START_NOT_STICKY
    }

    private fun startPlayback(url: String) {
        if (audioFocusHelper.requestAudioFocus()) {
            RadioPlayer.setMediaItem(url)
            RadioPlayer.play()
        }
    }

    private fun playNext() {
        if(isSura) {
            if (currentIndex < 113) currentIndex++
            startPlayback(reciterUrl + suraUrls[currentIndex].second)
        }else {
            currentIndex = (currentIndex + 1) % stations.size
            startPlayback(stations[currentIndex].url)
        }
    }
    private fun SkipForward() {
        if(isSura) {
            val player = RadioPlayer.getPlayer()
            player?.let {
                val newPosition = (it.currentPosition + 10000).coerceAtMost(it.duration)
                it.seekTo(newPosition)
            }
        }
    }
    private fun SkipBackward() {
        if(isSura) {
            val player = RadioPlayer.getPlayer()
            player?.let {
                val newPosition = (it.currentPosition - 10000).coerceAtLeast(0)
                it.seekTo(newPosition)
            }
        }
    }

    private fun playPrev() {
        if(isSura) {
            if (currentIndex > 0) currentIndex--
            startPlayback(reciterUrl + suraUrls[currentIndex].second)
        }else {
            currentIndex = if (currentIndex - 1 < 0) stations.lastIndex else currentIndex - 1
            startPlayback(stations[currentIndex].url)
        }
    }

    private fun buildNotification(): Notification {
        var currentPosition = 0L
        var duration = 0L

        if (isSura) {
            val player = RadioPlayer.getPlayer()
            player?.let {
                currentPosition = it.currentPosition
                duration = it.duration
            }

            return notificationManager.buildNotification(
                reciterName,
                audio?.title ?: "",
                true,
                currentPosition,
                duration
            )
        }

        return notificationManager.buildNotification(
            getString(R.string.anees_radio),
            stations[currentIndex].name,
            false
        )
    }

    private fun updateNotification() {
        val notification = buildNotification()
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(RadioNotificationManager.NOTIFICATION_ID, notification)
    }

    private fun sendPlaybackStateBroadcast(isPlaying: Boolean) {
        val intent = Intent(ACTION_PLAYBACK_STATE).apply {
            setPackage(packageName)
            putExtra(EXTRA_IS_PLAYING, isPlaying)
        }
        sendBroadcast(intent)
    }

    private fun sendStationChangedBroadcast(index: Int) {
        val intent = Intent(ACTION_STATION_CHANGED).apply {
            setPackage(packageName)
            putExtra(EXTRA_STATION_INDEX, index)
        }
        sendBroadcast(intent)
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
        stopForeground(true)
        stopSelf()
    }

    override fun onDestroy() {
        super.onDestroy()
        RadioPlayer.release()
        stopProgressUpdates()
        audioFocusHelper.abandonAudioFocus()
        mediaSession.release()
        sendPlaybackStateBroadcast(false)
        sendBroadcast(Intent(ACTION_CLOSE))
    }

}
