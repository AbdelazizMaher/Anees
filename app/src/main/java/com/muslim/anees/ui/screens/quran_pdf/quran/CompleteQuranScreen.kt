package com.muslim.anees.ui.screens.quran_pdf.quran

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.github.barteksc.pdfviewer.PDFView
import com.muslim.anees.ui.screens.quran_pdf.quran.components.BottomControlBar
import com.muslim.anees.ui.screens.quran_pdf.quran.components.PdfViewerFromAssets
import com.muslim.anees.ui.screens.quran_pdf.quran.components.TopControlBar
import com.muslim.anees.ui.screens.quran_pdf.quran.components.VerticalBookmarkBar

@Composable
fun QuranPDFViewerScreen(
    initPage: Int = 568,
    onIndexButtonClick: () -> Unit,
    onKhatmButtonClick: () -> Unit,
    onJuzButtonClick: () -> Unit
) {
    val context = LocalContext.current
    val viewModel: CompleteQuranViewModel = hiltViewModel()

    val pdfView = remember { PDFView(context, null) }

    LaunchedEffect(Unit) {
        viewModel.getPageBookmark()
    }

    val bookmark = viewModel.bookmark.collectAsStateWithLifecycle()

    var mutableInitPage by remember { mutableStateOf(initPage) }

    var controlsVisible by remember { mutableStateOf(false) }
    var pageNumber by remember { mutableStateOf(initPage) }

    LaunchedEffect(initPage) {
        pageNumber = initPage
    }

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
                .clickable { controlsVisible = !controlsVisible }
        ) {

            PdfViewerFromAssets(
                "q.pdf", pdfView, mutableInitPage,
                onPagerChange = {
                    pageNumber = it
                    viewModel.updateCurrentPageIndex(it)
                }
            ) {
                controlsVisible = !controlsVisible
            }
            if (pageNumber == bookmark.value) {
                VerticalBookmarkBar()
            }

            AnimatedVisibility(
                visible = controlsVisible,
                enter = fadeIn(),
                exit = fadeOut(),
                modifier = Modifier.align(Alignment.TopCenter)
            ) {
                TopControlBar(pageNumber)
            }

            AnimatedVisibility(
                visible = controlsVisible,
                enter = fadeIn(),
                exit = fadeOut(),
                modifier = Modifier.align(Alignment.BottomCenter)
            ) {
                BottomControlBar(
                    onIndexButtonClick,
                    onJuzButtonClick,
                    onKhatmButtonClick,
                    onBookmarkButtonClick = {
                        Toast.makeText(context, "تم إضافة علامه", Toast.LENGTH_SHORT).show()
                        viewModel.updatePageBookmark(pageNumber)
                        controlsVisible = !controlsVisible
                    },
                    onBookMoveClicked = {
                        controlsVisible = !controlsVisible
                        pdfView.jumpTo(bookmark.value)
                    }
                )
            }
        }

    }
}


