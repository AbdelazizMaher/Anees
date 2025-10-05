package com.example.anees.ui.screens.reciters.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdok.atmosphere.data.local.sharedPreference.ISharedPreferences
import com.example.anees.enums.Recitations
import com.example.anees.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReciterViewModel @Inject constructor(
    private val sharedPreferences: ISharedPreferences
) : ViewModel() {

    private val _selectedRecitation =
        MutableStateFlow(Recitations.HAFS_AN_ASIM)
    val selectedRecitation = _selectedRecitation.asStateFlow()

    init {
        viewModelScope.launch {
            val savedName = sharedPreferences.fetchData(
                Constants.KEY_SELECTED_RECITATION,
                Recitations.HAFS_AN_ASIM.name
            )
            val recitation = Recitations.values().find { it.name == savedName }
            recitation?.let { _selectedRecitation.value = it }
        }
    }

    fun setSelectedRecitation(recitation: Recitations) {
        _selectedRecitation.value = recitation
        viewModelScope.launch {
            sharedPreferences.saveData(Constants.KEY_SELECTED_RECITATION, recitation.name)
        }
    }
}