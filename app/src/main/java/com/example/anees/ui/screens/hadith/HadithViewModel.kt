package com.example.anees.ui.screens.hadith

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.anees.data.model.EditionResponse
import com.example.anees.data.model.HadithEntity
import com.example.anees.data.model.Response
import com.example.anees.data.repository.RepositoryImpl
import com.example.anees.enums.AuthorEdition
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HadithViewModel @Inject constructor(private val repo: RepositoryImpl): ViewModel() {
    private val _sectionsState = MutableStateFlow<Response<EditionResponse>>(Response.Loading)
    val sectionsState = _sectionsState.asStateFlow()

    private val _messageState = MutableStateFlow<String?>(null)
    val messageState = _messageState.asStateFlow()

    val savedHadith: StateFlow<List<HadithEntity>> = repo
        .getSavedHadithFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(1000), emptyList())


    fun toggleSave(hadith: String) {
        viewModelScope.launch {
            repo.toggleHadith(hadith)
        }
    }

    fun getSections(author: AuthorEdition) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repo.getAllSections(author.apiKey)
                    .catch {
                        _sectionsState.value = Response.Error(it.message.toString())
                    }
                    .collect {
                        _sectionsState.value = Response.Success(it)
                    }
            }catch (e: Exception){
                _messageState.value = e.message
            }
        }

    }


}