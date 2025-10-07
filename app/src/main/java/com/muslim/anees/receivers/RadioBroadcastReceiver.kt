package com.muslim.anees.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class RadioBroadcastReceiver(private val onPlayPause: (isPlaying: Boolean) -> Unit, private val onStationChanged: (index: Int) -> Unit) : BroadcastReceiver() {
    companion object {
        const val ACTION_PLAYBACK_STATE = "com.example.anees.ACTION_PLAYBACK_STATE"
        const val ACTION_STATION_CHANGED = "com.example.anees.ACTION_STATION_CHANGED"
        const val EXTRA_IS_PLAYING = "extra_is_playing"
        const val EXTRA_STATION_INDEX = "extra_station_index"
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        when (intent?.action) {
            ACTION_PLAYBACK_STATE -> {
                val isPlaying = intent.getBooleanExtra(EXTRA_IS_PLAYING, false)
                onPlayPause(isPlaying)
            }
            ACTION_STATION_CHANGED -> {
                val stationIndex = intent.getIntExtra(EXTRA_STATION_INDEX, 0)
                onStationChanged(stationIndex)
            }
        }
    }
}