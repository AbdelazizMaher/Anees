package com.muslim.anees.utils.media_helper

import android.content.Context
import android.content.Intent
import android.os.Build
import com.muslim.anees.data.model.audio.AudioTrack
import com.muslim.anees.services.RadioService

object RadioServiceManager {
    fun startRadioService(context: Context, audio: AudioTrack, isRadio: Boolean = true) {
        val intent = Intent(context, RadioService::class.java).apply {
            putExtra("audio", audio)
            putExtra("isRadio", isRadio)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intent)
        }else {
            context.startService(intent)
        }
    }

    fun sendRadioAction(context: Context, action: String) {
        val intent = Intent(context, RadioService::class.java).apply {
            this.action = action
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intent)
        } else {
            context.startService(intent)
        }
    }
}