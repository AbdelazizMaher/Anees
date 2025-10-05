package com.example.anees.ui.screens.eLMahfogat

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.anees.data.model.HadithEntity
import com.example.anees.data.model.audio.AudioDto
import com.example.anees.data.repository.Repository
import com.example.anees.utils.azkar_helper.AzkarUtils
import com.example.anees.utils.downloaded_audio.loadAllAudio
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ElMahfogatViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _azkarCategories = MutableStateFlow<List<String>>(emptyList())
    val azkarCategories: StateFlow<List<String>> = _azkarCategories.asStateFlow()

    private val _savedHadith = MutableStateFlow<List<HadithEntity>>(emptyList())
    val savedHadith: StateFlow<List<HadithEntity>> = _savedHadith.asStateFlow()

    private val _audioList = MutableStateFlow<List<AudioDto>>(emptyList())
    val audioList: StateFlow<List<AudioDto>> = _audioList.asStateFlow()


    fun loadAzkarCategories(context: Context) {
        viewModelScope.launch {
            repository.getSavedAzkarFlow()
                .map { list -> list.map { it.category } }
                .collect { savedCategories ->
                    val azkarList = AzkarUtils.parseAdhkar(context)
                    val allCategories = AzkarUtils.getAdhkarCategories(azkarList)
                    _azkarCategories.value = allCategories.filter { it in savedCategories }
                }
        }
    }

    fun loadAudio(context: Context) {
        viewModelScope.launch {
            _audioList.value = loadAllAudio(context)
        }
    }

     fun loadSavedHadiths() {
        viewModelScope.launch {
            repository.getSavedHadithFlow()
                .collect { hadithList ->
                    _savedHadith.value = hadithList
                }
        }
    }

    fun toggleAzkarSaved(category: String) {
        viewModelScope.launch {
            repository.toggleAzkar(category)
        }
    }

    fun toggleHadithSaved(hadith: String) {
        viewModelScope.launch {
            repository.toggleHadith(hadith)
        }
    }
}
