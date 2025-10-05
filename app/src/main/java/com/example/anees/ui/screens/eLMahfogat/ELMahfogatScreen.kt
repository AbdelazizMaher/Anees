package com.example.anees.ui.screens.eLMahfogat

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.anees.ui.screens.eLMahfogat.component.ElMahafogatDownloadedAudioCard
import com.example.anees.ui.screens.eLMahfogat.component.ElMahafogatZekrTitleCard
import com.example.anees.ui.screens.eLMahfogat.component.ElMahfogatTopBar
import com.example.anees.ui.screens.eLMahfogat.component.EmptyPlaceholder
import com.example.anees.ui.screens.hadith.components.HadithCard
import com.example.anees.ui.screens.radio.components.ScreenBackground
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ElMahfogatScreen(
    navToHome: () -> Unit = {},
    navToAzkarDetails: (String) -> Unit = {},
    navToReciter: (index: Int) -> Unit,
    viewModel: ElMahfogatViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val azkarCategories by viewModel.azkarCategories.collectAsStateWithLifecycle()
    val hadithContent by viewModel.savedHadith.collectAsStateWithLifecycle()

    val tabs = listOf("القراءات","الأذكار", "الأحاديث")
    val pagerState = rememberPagerState(initialPage = 0) { tabs.size }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(pagerState.currentPage) {
        when (pagerState.currentPage) {
            0 -> {
                viewModel.loadAudio(context)
            }
            1 -> {
                viewModel.loadAzkarCategories(context)
            }
            2 -> {
                viewModel.loadSavedHadiths()
            }
        }
    }

    ScreenBackground()

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
        ) {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                ElMahfogatTopBar(title = "المحفوظات", onBackClick = { navToHome() })
            }

            TabRow(
                selectedTabIndex = pagerState.currentPage,
                containerColor = Color.Transparent,
                indicator = {},
                divider = {},
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .border(border = BorderStroke(1.dp, Color.LightGray), shape = RoundedCornerShape(24.dp))
            ) {
                tabs.forEachIndexed { index, title ->
                    val selected = pagerState.currentPage == index
                    Tab(
                        selected = selected,
                        selectedContentColor = Color.LightGray,
                        onClick = {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        },
                        text = {
                            Box(
                                modifier = Modifier
                                    .background(
                                        color = if (selected) Color(0xEB803F0B) else Color.Transparent,
                                        shape = RoundedCornerShape(24.dp)
                                    )
                                    .padding(horizontal = 16.dp, vertical = 8.dp)
                            ) {
                                Text(
                                    text = title,
                                    color = if (selected) Color.White else Color.DarkGray
                                )
                            }
                        }
                    )
                }
            }

            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) { page ->
                when (page) {
                    0 -> {
                        val audioList = viewModel.audioList.collectAsStateWithLifecycle().value
                        if (audioList.isEmpty()) {
                            EmptyPlaceholder("لا توجد قراءات محفوظة")
                        } else {
                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                itemsIndexed(audioList) { index, audio ->
                                    ElMahafogatDownloadedAudioCard(
                                        surah = audio,
                                        index = index,
                                        onClick = navToReciter
                                    )
                                }
                            }
                        }
                    }

                    1 -> {
                        if (azkarCategories.isEmpty()) {
                            EmptyPlaceholder("لا يوجد أذكار محفوظة")
                        } else {
                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                items(azkarCategories) { category ->
                                    ElMahafogatZekrTitleCard(
                                        title = category,
                                        isSaved = true,
                                        onSaveClick = { viewModel.toggleAzkarSaved(category) },
                                        navToAzkarDetails = { navToAzkarDetails(category) }
                                    )
                                }
                            }
                        }
                    }

                    2 -> {
                        if (hadithContent.isEmpty()) {
                            EmptyPlaceholder("لا يوجد أحاديث محفوظة")
                        } else {
                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                items(hadithContent) { content ->
                                    HadithCard(
                                        hadithText = content.title,
                                        isSaved = true,
                                        onSaveClick = { viewModel.toggleHadithSaved(content.title) }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
