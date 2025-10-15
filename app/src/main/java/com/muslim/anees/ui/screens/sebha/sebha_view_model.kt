package com.muslim.anees.ui.screens.sebha

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muslim.anees.data.model.Sebiha
import com.muslim.anees.data.repository.RepositoryImpl
import com.muslim.anees.utils.sebha_helper.azkarList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SebihaViewModel @Inject constructor(private val repo: RepositoryImpl): ViewModel() {
    private val _sebiha= MutableStateFlow(Sebiha(0,0,0, 0,azkarList.first().arabicName))
    val sebiha=_sebiha.asStateFlow()
    private val _error= MutableStateFlow("")
    val error=_error.asStateFlow()
    init {
        getSebiha()
    }

     private fun getSebiha(){
         viewModelScope.launch {
             repo.getSebiha().catch {
                 _error.value=it.message.toString()
             }.collect {
                 _sebiha.value = it?: Sebiha(0,0,0, 0,azkarList.first().arabicName)
             }
         }
    }
    fun addSebiha(sebiha:Sebiha){
        viewModelScope.launch {
            repo.addSebiha(sebiha)
        }
    }



}
