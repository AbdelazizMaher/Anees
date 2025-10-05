package com.example.anees.ui.screens.radio

import android.app.Application
import android.content.Context
import android.content.IntentFilter
import android.os.Build
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import com.example.anees.data.model.radio.RadioStations
import com.example.anees.data.model.radio.audioStations
import com.example.anees.receivers.RadioBroadcastReceiver
import com.example.anees.services.RadioService
import com.example.anees.utils.media_helper.RadioPlayer
import com.example.anees.utils.media_helper.RadioServiceManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class RadioViewModel @Inject constructor(private val context: Application) : AndroidViewModel(context) {

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying = _isPlaying.asStateFlow()

    private val radioStations = RadioStations.stations
    private var currentIndex = 0

    private val _currentStation = MutableStateFlow(radioStations[currentIndex])
    val currentStation = _currentStation.asStateFlow()

    private var broadcastReceiver: RadioBroadcastReceiver? = null

    init {
        setupBroadcastReceiver()
        RadioServiceManager.startRadioService(
            context,
            audioStations[currentIndex]
        )
        _isPlaying.value = true
        currentIndex = 0
        _currentStation.value = radioStations[0]
        RadioPlayer.setMediaItem(radioStations[0].url)
    }

    fun playPauseRadio() {
        if (_isPlaying.value) {
            RadioServiceManager.sendRadioAction(context, RadioService.ACTION_PAUSE)
            _isPlaying.value = false
        } else {
            RadioServiceManager.sendRadioAction(context, RadioService.ACTION_PLAY)
            _isPlaying.value = true
        }
    }

    fun nextStation() {
        currentIndex = (currentIndex + 1) % radioStations.size
        _currentStation.value = radioStations[currentIndex]
        startRadioService()
    }

    fun previousStation() {
        currentIndex = if (currentIndex - 1 < 0) radioStations.lastIndex else currentIndex - 1
        _currentStation.value = radioStations[currentIndex]
        startRadioService()
    }

    private fun startRadioService() {
        RadioServiceManager.startRadioService(context, audioStations[currentIndex])
        _isPlaying.value = true
    }


    private fun setupBroadcastReceiver() {
        broadcastReceiver = RadioBroadcastReceiver(
            onPlayPause = { isPlaying ->
                _isPlaying.value = isPlaying
            },
            onStationChanged = { index ->
                currentIndex = index
                _currentStation.value = radioStations[index]
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

    override fun onCleared() {
        super.onCleared()
        RadioPlayer.release()
        broadcastReceiver?.let {
            context.unregisterReceiver(it)
        }
    }
}






