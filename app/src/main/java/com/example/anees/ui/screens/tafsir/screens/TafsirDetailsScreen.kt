package com.example.anees.ui.screens.tafsir.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.anees.data.model.Response
import com.example.anees.data.model.TafsierModel
import com.example.anees.enums.QuranSurah
import com.example.anees.ui.screens.hadith.components.ScreenTitle
import com.example.anees.ui.screens.radio.components.ScreenBackground
import com.example.anees.ui.screens.tafsir.TafsirViewModel
import com.example.anees.ui.screens.tafsir.component.TafsirDeatilsCard

@Composable
fun TafsirDetailsScreen(surah: QuranSurah,navToHome:()->Unit) {

    val viewModel: TafsirViewModel = hiltViewModel()
    val tafsir by viewModel.tafsirSurah.collectAsStateWithLifecycle()

    LaunchedEffect(surah.number) {
        viewModel.loadTafsir(surah.number.toInt())
    }

    when (val state = tafsir) {
        is Response.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize()
            ) {
                CircularProgressIndicator()
            }
        }

        is Response.Success -> {
            Screen(surah, navToHome,state.data)
        }

        is Response.Error -> {
            Box (
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()

            ){
                Text(
                    text = state.message,
                    color = Color.Red,
                    textAlign = TextAlign.Center,
                    fontSize = 24.sp,
                    modifier = Modifier.padding(horizontal = 32.dp)
                )
            }
        }

    }


}

@Composable
fun Screen(surah: QuranSurah, navToHome:()->Unit, tafsir: TafsierModel) {
    Box(Modifier.fillMaxSize()){
        ScreenBackground()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(top = 24.dp)
        ) {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                ScreenTitle( title =  "سُورَةٌ ${surah.arabicName}",
                     onBackClick = { navToHome() }, size = 24)
            }
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                items(tafsir.result) {
                    TafsirDeatilsCard(it)
                }
                item {
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }
}
