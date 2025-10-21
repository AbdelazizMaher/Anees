package com.muslim.anees.ui.screens.sebha

import androidx.compose.runtime.asIntState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muslim.anees.R
import com.muslim.anees.data.model.Sebiha
import com.muslim.anees.data.model.SebihaZekr
import com.muslim.anees.data.repository.RepositoryImpl
import com.muslim.anees.utils.Constants
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

    private val _sebhaImageId = mutableStateOf(getChachedSebhaImageId())
    val sebhaImageId = _sebhaImageId.asIntState()

    init {
        cashInitialList()
        getSebiha()
    }


    private fun cashInitialList() {
        viewModelScope.launch {
            val list = repo.getAllZekrFromSebha().first()
            if (list.isEmpty()) {
                azkarList.forEach {
                    repo.insertZekarInSebha(SebihaZekr(it.arabicName))
                }
            }
        }
    }


    fun cashSebhaImageId(imageId: Int) {
        repo.saveData(Constants.SEBHA_IMAGE_ID_KEY, imageId)
        _sebhaImageId.value = imageId
    }

    fun getChachedSebhaImageId(): Int {
        return repo.fetchData(Constants.SEBHA_IMAGE_ID_KEY, R.drawable.sebha)
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
