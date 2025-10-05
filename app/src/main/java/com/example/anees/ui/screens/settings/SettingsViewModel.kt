package com.example.anees.ui.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.anees.data.repository.Repository
import com.example.anees.enums.AzanRecitersEnum
import com.example.anees.enums.FajrRecitersEnum
import com.example.anees.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SettingsViewModel @Inject constructor(val repository: Repository) : ViewModel() {


    private var _currentAzanReciter = MutableStateFlow(AzanRecitersEnum.Abdelbaset.label)
    val currentAzanReciter  = _currentAzanReciter.asStateFlow()

    private var _currentFajrReciter = MutableStateFlow(FajrRecitersEnum.Abdelbaset.label)
    val currentFajrReciter  = _currentFajrReciter.asStateFlow()

    private var _currentZekirInterval = MutableStateFlow(1)
    val currentZekirInterval  = _currentZekirInterval.asStateFlow()

    private var _azanNotificationState = MutableStateFlow(true)
    val azanNotificationState  = _azanNotificationState.asStateFlow()

    private var _zekrNotificationState = MutableStateFlow(true)
    val zekrNotificationState  = _zekrNotificationState.asStateFlow()


    init {
        viewModelScope.launch(Dispatchers.IO){
            _currentAzanReciter.value = repository.fetchData(Constants.CURRENT_AZAN_RECITER , AzanRecitersEnum.Abdelbaset.label)
            _currentZekirInterval.value = repository.fetchData(Constants.CURRENT_ZEKIR_INTERVAL , 1)
            _azanNotificationState.value = repository.fetchData(Constants.AZAN_NOTIFICATION_STATE , false)
            _zekrNotificationState.value = repository.fetchData(Constants.NOTIFICATION_STATE , false)
            _currentFajrReciter.value = repository.fetchData(Constants.CURRENT_FAJR_RECITER , FajrRecitersEnum.Abdelbaset.label)
        }
    }

    fun updateAzanNotificationState(state:Boolean){
        viewModelScope.launch(Dispatchers.IO){
            _azanNotificationState.value = state
            repository.saveData(Constants.AZAN_NOTIFICATION_STATE,state)
        }
    }
    fun updateZekirNotificationState(state:Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            _zekrNotificationState.value = state
            repository.saveData(Constants.NOTIFICATION_STATE, state)
        }
    }
    fun updateCurrentZekirInterval(interval: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _currentZekirInterval.value = interval
            repository.saveData(Constants.CURRENT_ZEKIR_INTERVAL, interval)
        }
    }
    fun updateCurrentAzanReciter(reciter: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _currentAzanReciter.value = reciter
            repository.saveData(Constants.CURRENT_AZAN_RECITER, reciter)
        }
    }
    fun updateCurrentFajrReciter(reciter: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _currentFajrReciter.value = reciter
            repository.saveData(Constants.CURRENT_FAJR_RECITER, reciter)
        }
    }


}