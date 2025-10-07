package com.muslim.anees.ui.screens.quran_pdf.quran.components

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.muslim.anees.utils.pdf_helper.getSurahAndJuzForPage

@Composable
fun TopControlBar(page: Int) {



    val result = getSurahAndJuzForPage(page)
    Log.d("page $page,result", result.toString())
    val surahName = result?.first ?: ""
    val juzName = result?.second ?: ""

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {


        Surface(color = Color.Black.copy(alpha = 0.8f), modifier = Modifier.fillMaxWidth()) {
            Row(
                Modifier.fillMaxWidth().padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "$juzName",
                    color = Color.White,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Start
                )
                Text(
                    text = "$surahName",
                    color = Color.White,
                    fontSize = 18.sp,
                )
            }
        }

    }
}
