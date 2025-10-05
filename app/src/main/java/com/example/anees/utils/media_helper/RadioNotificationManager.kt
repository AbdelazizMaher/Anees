package com.example.anees.utils.media_helper

import android.app.ActivityManager
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.ACTIVITY_SERVICE
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.support.v4.media.session.MediaSessionCompat
import androidx.core.app.NotificationCompat
import com.example.anees.R
import com.example.anees.services.RadioService
import com.example.anees.services.RadioService.Companion.ACTION_CLOSE
import com.example.anees.services.RadioService.Companion.ACTION_NEXT
import com.example.anees.services.RadioService.Companion.ACTION_PAUSE
import com.example.anees.services.RadioService.Companion.ACTION_PLAY
import com.example.anees.services.RadioService.Companion.ACTION_PREV
import com.example.anees.services.RadioService.Companion.ACTION_SKIP_BACKWARD
import com.example.anees.services.RadioService.Companion.ACTION_SKIP_FORWARD

class RadioNotificationManager(
    private val context: Context,
    private val mediaSession: MediaSessionCompat
) {

    companion object {
        const val CHANNEL_ID = "radio_channel"
        const val NOTIFICATION_ID = 1
    }

    init {
        createNotificationChannel()
    }

    fun buildNotification(title: String, subtitle: String, showSkipButtons: Boolean, currentPosition: Long = 0,
                          duration: Long = 0): Notification {
        val playPauseAction = if (RadioPlayer.isPlaying()) {
            NotificationCompat.Action(
                R.drawable.play_notification, "Pause", notificationIntent(ACTION_PAUSE)
            )
        } else {
            NotificationCompat.Action(
                R.drawable.pause_notification, "Play", notificationIntent(ACTION_PLAY)
            )
        }

        val nextAction = NotificationCompat.Action(
            R.drawable.next_notification, "Next", notificationIntent(ACTION_NEXT)
        )
        val prevAction = NotificationCompat.Action(
            R.drawable.prev_notification, "Previous", notificationIntent(ACTION_PREV)
        )

        val closeAction = NotificationCompat.Action(
            R.drawable.ic_close, "Close", closeIntent()
        )

        val actions = mutableListOf<NotificationCompat.Action>().apply {
            add(prevAction)

            if (showSkipButtons) {
                add(NotificationCompat.Action(
                    R.drawable.fast_rewind_24, "Skip Backward", notificationIntent(ACTION_SKIP_BACKWARD)
                ))
            }

            add(playPauseAction)

            if (showSkipButtons) {
                add(NotificationCompat.Action(
                    R.drawable.fast_forward_24, "Skip Forward", notificationIntent(ACTION_SKIP_FORWARD)
                ))
            }

            add(nextAction)
            add(closeAction)
        }

        val compactViewIndices = if (showSkipButtons) {
            intArrayOf(1, 3, 5)
        } else {
            intArrayOf(0, 1, 3)
        }

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(subtitle)
            .setSmallIcon(R.drawable.logo_foreground)
            .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.drawable.zekrback))
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setStyle(
                androidx.media.app.NotificationCompat.MediaStyle()
                    .setMediaSession(mediaSession.sessionToken)
                    .setShowActionsInCompactView(*compactViewIndices)
            )
            .setOnlyAlertOnce(true)
            .setOngoing(isAppInForeground())

        if (showSkipButtons && duration > 0) {
            builder.setProgress(
                duration.toInt(),
                currentPosition.toInt(),
                false
            )

            val timeText = formatTime(currentPosition) + " / " + formatTime(duration)
            builder.setContentText("$subtitle • $timeText")
        }

        actions.forEach { action ->
            builder.addAction(action)
        }

        return builder.build()
    }

    private fun formatTime(millis: Long): String {
        val totalSeconds = millis / 1000
        val minutes = totalSeconds / 60
        val seconds = totalSeconds % 60
        return String.format("%d:%02d", minutes, seconds)
    }
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID, "Radio Channel", NotificationManager.IMPORTANCE_LOW
            )
            val manager = context.getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }

    private fun notificationIntent(action: String): PendingIntent {
        val intent = Intent(context, RadioService::class.java).apply {
            this.action = action
        }
        return PendingIntent.getService(context, action.hashCode(), intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
    }

    private fun closeIntent(): PendingIntent {
        val intent = Intent(context, RadioService::class.java).apply {
            action = ACTION_CLOSE
        }
        return PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
    }

    private fun isAppInForeground(): Boolean {
        val activityManager = context.getSystemService(ACTIVITY_SERVICE) as ActivityManager
        val runningProcesses = activityManager.runningAppProcesses ?: return false
        val packageName = context.packageName

        for (processInfo in runningProcesses) {
            if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND &&
                processInfo.processName == packageName) {
                return true
            }
        }
        return false
    }
}