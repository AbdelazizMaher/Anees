package com.example.anees.ui.screens.azan

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
class AzanViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private var _currentAzanReciter = MutableStateFlow(AzanRecitersEnum.Abdelbaset.label)
    val currentAzanReciter = _currentAzanReciter.asStateFlow()

    private var _currentFajrReciter = MutableStateFlow(FajrRecitersEnum.Abdelbaset.label)
    val currentFajrReciter = _currentFajrReciter.asStateFlow()


    fun getCurrentAzanReciter(){
        viewModelScope.launch(Dispatchers.IO){
            _currentAzanReciter.value = repository.fetchData(Constants.CURRENT_AZAN_RECITER , AzanRecitersEnum.Abdelbaset.label)
            _currentFajrReciter.value = repository.fetchData(Constants.CURRENT_FAJR_RECITER , FajrRecitersEnum.Abdelbaset.label)
        }
    }

}