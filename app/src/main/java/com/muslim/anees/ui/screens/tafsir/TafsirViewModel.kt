package com.muslim.anees.ui.screens.tafsir

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muslim.anees.data.model.Response
import com.muslim.anees.data.model.TafsierModel
import com.muslim.anees.data.repository.RepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TafsirViewModel @Inject constructor(private val repo: RepositoryImpl) : ViewModel() {
    private val _tafsirSurah = MutableStateFlow<Response<TafsierModel>>(Response.Loading)
    val tafsirSurah = _tafsirSurah.asStateFlow()


    fun addTafsir(id: Int, tafsir: TafsierModel) {
        viewModelScope.launch {
            tafsir.id = id
            repo.addTafsir(tafsir)
        }
    }

    fun loadTafsir(id: Int) {
        viewModelScope.launch {
            repo.getTafsir(id).collect { localData ->
                if (localData != null && localData.result.isNotEmpty()) {
                    _tafsirSurah.value = Response.Success(localData)
                } else {
                    try {
                        repo.getAllTafsier(id.toString()).collect { apiData ->
                            addTafsir(id, apiData)
                            _tafsirSurah.value = Response.Success(apiData)
                        }
                    } catch (e: Exception) {
                        _tafsirSurah.value =
                            Response.Error("يرجى الاتصال بالإنترنت للحصول على البيانات لأول مرة")
                    }
                }
            }
        }
    }


}