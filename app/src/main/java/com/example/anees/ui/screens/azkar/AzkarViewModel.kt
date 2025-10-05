package com.example.anees.ui.screens.azkar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.anees.data.model.AzkarEntity
import com.example.anees.data.repository.Repository
import com.example.anees.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AzkarViewModel @Inject constructor(private val repository: Repository) : ViewModel() {


    private var _zekrNotificationState = MutableStateFlow(true)
    val zekrNotificationState  = _zekrNotificationState.asStateFlow()

    private var _currentZekirInterval = MutableStateFlow(1)
    val currentZekirInterval  = _currentZekirInterval.asStateFlow()

    val savedZekr: StateFlow<List<AzkarEntity>> = repository
        .getSavedAzkarFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(1000), emptyList())

    fun toggleSave(category: String) {
        viewModelScope.launch {
            repository.toggleAzkar(category)
        }
    }

    fun getData(){
        viewModelScope.launch(Dispatchers.IO){
            _zekrNotificationState.value = repository.fetchData(Constants.NOTIFICATION_STATE , true)
            _currentZekirInterval.value = repository.fetchData(Constants.CURRENT_ZEKIR_INTERVAL , 1)
        }
    }

}