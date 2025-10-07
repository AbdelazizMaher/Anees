package com.muslim.anees.ui.screens.quran_pdf.quran

import android.util.Log
import androidx.lifecycle.ViewModel
import com.muslim.anees.data.repository.Repository
import com.muslim.anees.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject


@HiltViewModel
class CompleteQuranViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _bookmark = MutableStateFlow(900)
    val bookmark = _bookmark.asStateFlow()

    fun updateCurrentPageIndex(index: Int) {
        repository.saveData(
            key = Constants.CURRENT_PAGE_INDEX,
            value = index
        )
    }
    fun updatePageBookmark(index: Int) {
        Log.e("TAG", "updatePageBookmark: $index" )
        repository.saveData(
            key = Constants.PAGE_BOOKMARK,
            value = index
        )
        _bookmark.value = index
    }

    fun getPageBookmark() {
       val result = repository.fetchData(Constants.PAGE_BOOKMARK , 900)
        _bookmark.value = result
        Log.e("TAG", "getPageBookmark: $result" )
    }


}