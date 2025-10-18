package com.muslim.anees.ui.screens.sebha

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muslim.anees.data.model.Sebiha
import com.muslim.anees.data.model.SebihaZekr
import com.muslim.anees.data.repository.RepositoryImpl
import com.muslim.anees.utils.sebha_helper.azkarList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SebihaViewModel @Inject constructor(private val repo: RepositoryImpl) : ViewModel() {
    private val _sebiha = MutableStateFlow(Sebiha(0, 0, 0, 0, azkarList.first().arabicName))
    val sebiha = _sebiha.asStateFlow()
    private val _error = MutableStateFlow("")


    private val _allSebhaZeker = MutableStateFlow(emptyList<SebihaZekr>())
    val allSebhaZeker = _allSebhaZeker.asStateFlow()

    init {
        cashInitialList()
        getSebiha()
    }


    fun cashInitialList() {
        viewModelScope.launch {
            val list = repo.getAllZekrFromSebha().first()
            if (list.isEmpty()) {
                azkarList.forEach {
                    repo.insertZekarInSebha(SebihaZekr(it.arabicName))
                }
            }
        }
    }



    fun getAllAzkarFromDb() {
        viewModelScope.launch {
            repo.getAllZekrFromSebha().catch {
                _error.value = it.message.toString()
            }.collect {
                _allSebhaZeker.value = it
            }

        }
    }

    fun addZekerToSebha(sebihaZekr: SebihaZekr) {
        viewModelScope.launch {
            repo.insertZekarInSebha(sebihaZekr)
            getAllAzkarFromDb()
        }
    }

    fun deleteZekerFromSebha(sebihaZekr: SebihaZekr) {
        viewModelScope.launch {
            repo.deleteZekarfromSebha(sebihaZekr)
            getAllAzkarFromDb()
        }
    }

    private fun getSebiha() {
        viewModelScope.launch {
            repo.getSebiha().catch {
                _error.value = it.message.toString()
            }.collect {
                _sebiha.value = it ?: Sebiha(0, 0, 0, 0, azkarList.first().arabicName)
            }
        }
    }

    fun addSebiha(sebiha: Sebiha) {
        viewModelScope.launch {
            repo.addSebiha(sebiha)
        }
    }


}
