package com.muslim.anees.ui.screens.tafsir.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.muslim.anees.enums.QuranSurah
import com.muslim.anees.ui.screens.hadith.components.ScreenTitle
import com.muslim.anees.ui.screens.radio.components.ScreenBackground
import com.muslim.anees.ui.screens.tafsir.component.TafsirCard
import com.google.gson.Gson

@Composable
fun TafsirScreen(navToHome:()->Unit,navToDetails: (String) -> Unit) {

    val surhaList = QuranSurah.entries

    ScreenBackground()
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(vertical = 24.dp)
        ) {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                ScreenTitle( title =  "التفسير الميسر للقران الكريم", size = 22,onBackClick = { navToHome() })
            }

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(bottom = 16.dp),
                modifier = Modifier.padding(bottom = 12.dp)
            ) {

                items(surhaList) { surah ->
                    TafsirCard(
                        surah = surah,
                        onClick = {
                            navToDetails(Gson().toJson(surah))
                        }
                    )
                }
            }
        }}

}