package com.muslim.anees.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(): ViewModel() {

    var _uiState = MutableStateFlow(true)
    val uiState = _uiState.asStateFlow()

    fun refreshPermission(hasPermission: Boolean) {
        viewModelScope.launch {
            _uiState.emit(hasPermission)
        }
    }
}