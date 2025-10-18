package com.muslim.anees.ui.screens.azkar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muslim.anees.data.model.AzkarEntity
import com.muslim.anees.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AzkarViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    val savedZekr: StateFlow<List<AzkarEntity>> = repository
        .getSavedAzkarFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(1000), emptyList())

    fun toggleSave(category: String) {
        viewModelScope.launch {
            repository.toggleAzkar(category)
        }
    }


}