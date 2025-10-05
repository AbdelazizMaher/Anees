package com.example.anees.ui.screens.reciters.view_model

import android.app.Application
import android.app.DownloadManager
import android.content.Context
import android.content.IntentFilter
import android.os.Build
import android.os.Environment
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.anees.data.model.RecitationModel
import com.example.anees.data.model.audio.AudioTrack
import com.example.anees.data.model.audio.toAudioTrack
import com.example.anees.receivers.RadioBroadcastReceiver
import com.example.anees.services.RadioService
import com.example.anees.utils.downloaded_audio.loadAllAudio
import com.example.anees.utils.media_helper.RadioPlayer
import com.example.anees.utils.media_helper.RadioServiceManager
import com.example.anees.utils.pdf_helper.SuraIndexes
import com.example.anees.utils.sura_mp3_helper.suraUrls
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuranPlayerViewModel @Inject constructor(private val context: Application) :
    AndroidViewModel(context) {

    private val _playList = MutableStateFlow<List<AudioTrack>>(emptyList())
    val playList = _playList.asStateFlow()

    private val _currentSuraIndex = MutableStateFlow(0)
    val currentSuraIndex = _currentSuraIndex.asStateFlow()

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying = _isPlaying.asStateFlow()

    private val _progress = MutableStateFlow(0f)
    val progress = _progress.asStateFlow()

    private var isProgressActive = true

    private var broadcastReceiver: RadioBroadcastReceiver? = null


    val currentTrack: StateFlow<AudioTrack?> = combine(_playList, _currentSuraIndex) { playList, index ->
        playList.getOrNull(index)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)

    init {
        setupBroadcastReceiver()
    }

    fun setOnlinePlaylist(reciter: RecitationModel, description: String) {
        val suras = SuraIndexes.mapIndexed { index, sura ->
            AudioTrack(
                index = index,
                title = sura.suraName,
                description = description,
                reciter = reciter.reciter.reciterName,
                reciterImage = reciter.reciter.imageUrl,
                typeIcon = sura.type.name,
                reciterBaseUrl = reciter.url,
                uri = reciter.url + suraUrls[index].second,
            )
        }
        _playList.value = suras
        _currentSuraIndex.value = 0
    }
    fun setOfflinePlaylist() {
        val suras = loadAllAudio(context).map {
            it.toAudioTrack(loadAllAudio(context).indexOf(it))
        }
        _playList.value = suras
        _currentSuraIndex.value = 0
    }

    fun playSura(index: Int) {
        if(index < 0 || index > _playList.value.lastIndex) return
        _currentSuraIndex.value = index
        val sura = _playList.value[index]
        RadioServiceManager.startRadioService(context, sura, false)
        _isPlaying.value = true
    }

    fun playPauseSura() {
        if (RadioPlayer.isPlaying()) {
            RadioServiceManager.sendRadioAction(context, RadioService.ACTION_PAUSE)
            _isPlaying.value = false
        } else {
            RadioServiceManager.sendRadioAction(context, RadioService.ACTION_PLAY)
            _isPlaying.value = true
        }
    }

    fun getNextSura() {
        viewModelScope.launch {
            isProgressActive = true
            while (isProgressActive) {
                val duration = RadioPlayer.getDuration()
                val position = RadioPlayer.getCurrentPosition()
                if (duration > 0) {
                    _progress.value = position / duration.toFloat()
                    if (position >= duration - 500 && _isPlaying.value) {
                        if (_currentSuraIndex.value < _playList.value.lastIndex) {
                            onNext()
                        }
                    }
                }
                delay(500)
            }
        }
    }

    fun onNext() {
        if (_currentSuraIndex.value < _playList.value.lastIndex) {
            playSura(_currentSuraIndex.value + 1)
        }
    }

    fun onPrev() {
        if (_currentSuraIndex.value > 0) {
            playSura(_currentSuraIndex.value - 1)
        }
    }

    private fun setupBroadcastReceiver() {
        broadcastReceiver = RadioBroadcastReceiver(
            onPlayPause = { isPlaying ->
                _isPlaying.value = isPlaying
            },
            onStationChanged = { index ->
                if (index != _currentSuraIndex.value) {
                    playSura(index)
                }
            }
        )

        val filter = IntentFilter().apply {
            addAction(RadioBroadcastReceiver.ACTION_PLAYBACK_STATE)
            addAction(RadioBroadcastReceiver.ACTION_STATION_CHANGED)
            priority = IntentFilter.SYSTEM_HIGH_PRIORITY
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            context.registerReceiver(broadcastReceiver, filter, Context.RECEIVER_NOT_EXPORTED)
        } else {
            ContextCompat.registerReceiver(context, broadcastReceiver, filter, ContextCompat.RECEIVER_NOT_EXPORTED)
        }
    }
    fun downloadCurrentSura() {
        val index = _currentSuraIndex.value
        val suraName = SuraIndexes[index].suraName
        val suraUrl =_playList.value[index].uri
        val fileName =
            "$suraName - ${_playList.value[index].reciter} - ${_playList.value[index].description}.mp3"
        val request = DownloadManager.Request(suraUrl.toUri())
            .setTitle("Downloading ${suraName + " " + _playList.value[index].reciter}")
            .setDescription("جارٍ تحميل السورة${suraName}")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        request.setDestinationInExternalPublicDir(
            Environment.DIRECTORY_MUSIC,
            "Anees/$fileName"
        )

        val dm = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        dm.enqueue(request)
    }

    override fun onCleared() {
        super.onCleared()
        isProgressActive = false
        RadioPlayer.release()
        broadcastReceiver?.let {
            context.unregisterReceiver(it)
        }
    }

    fun seekTo(milliseconds: Long) {
        RadioPlayer.seekTo(milliseconds)
    }

}