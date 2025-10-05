package com.example.anees.utils.media_helper

import android.content.Context
import android.media.AudioFocusRequest
import android.media.AudioManager
import android.os.Build

class AudioFocusHelper(
    private val context: Context,
    private val onFocusGain: () -> Unit,
    private val onFocusLoss: () -> Unit,
    private val onDuck: () -> Unit
) {
    private val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
    private var audioFocusRequest: AudioFocusRequest? = null


    private val audioFocusChangeListener = AudioManager.OnAudioFocusChangeListener { focusChange ->
        when (focusChange) {
            AudioManager.AUDIOFOCUS_GAIN -> {
                onFocusGain()
            }
            AudioManager.AUDIOFOCUS_LOSS -> {
                onFocusLoss()
            }
            AudioManager.AUDIOFOCUS_LOSS_TRANSIENT -> {
                onFocusLoss()
            }
            AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK -> {
                onDuck()
            }
        }
    }

    fun requestAudioFocus(): Boolean {
        val focusRequest = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN)
                .setOnAudioFocusChangeListener(audioFocusChangeListener)
                .setWillPauseWhenDucked(true)
                .setAcceptsDelayedFocusGain(true)
                .setAudioAttributes(RadioPlayer.getAudioAttributes())
                .build().also {
                    audioFocusRequest = it
                }
        }else {
            null
        }

        val result = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            audioManager.requestAudioFocus(focusRequest!!)
        } else {
            audioManager.requestAudioFocus(
                audioFocusChangeListener,
                AudioManager.STREAM_MUSIC,
                AudioManager.AUDIOFOCUS_GAIN
            )
        }

        return result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED
    }

    fun abandonAudioFocus() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            audioFocusRequest?.let {
                audioManager.abandonAudioFocusRequest(it)
                audioFocusRequest = null
            }
        } else {
            audioManager.abandonAudioFocus(audioFocusChangeListener)
        }
    }
}

