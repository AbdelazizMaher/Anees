package com.example.anees.ui.screens.quran_pdf.quran_index

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.anees.R
import com.example.anees.enums.SuraTypeEnum
import com.example.anees.ui.screens.radio.components.ScreenBackground
import com.example.anees.utils.extensions.convertNumbersToArabic
import com.example.anees.utils.pdf_helper.SuraIndex
import com.example.anees.utils.pdf_helper.SuraIndexes

@Composable
fun QuranIndexScreen(onIndexButtonClick: () -> Unit) {
    val quranIndexViewModel : QuranIndexViewModel = hiltViewModel()
    ScreenBackground()
    LazyColumn(
        modifier = Modifier.fillMaxWidth()
    ) {
        item {
            Spacer(modifier = Modifier.height(48.dp).fillMaxWidth()
            )
        }
        items(SuraIndexes){
            SurahRow(sura = it){
                quranIndexViewModel.updateCurrentPageIndex(it)
                onIndexButtonClick()
            }
        }
        item {
            Spacer(modifier = Modifier.height(48.dp).fillMaxWidth()
            )
        }
    }

}

@SuppressLint("UnrememberedMutableInteractionSource")
@Composable
fun SurahRow(
    sura:SuraIndex,
    onClick:(Int)-> Unit
) {

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .clickable(
                    indication = null,
                    interactionSource = MutableInteractionSource(),
                    onClick = {
                        onClick(sura.pageNumber)
                    }
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(16.dp))

            Image(
                painter = painterResource(
                    id = if (sura.type == SuraTypeEnum.MECCA)
                        R.drawable.kaaba else R.drawable.mosque
                ),
                contentDescription = "Surah Icon",
                modifier = Modifier.size(32.dp).weight(1f)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "آياتها",
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.End
                )
                Text(
                    text = "${sura.ayahNumber}".convertNumbersToArabic(),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.End
                )
            }

            Text(
                text = "  سوره ${sura.suraName}",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(end = 16.dp).weight(3f),
                textAlign = TextAlign.End
            )

            Text(
                text = "${sura.index}",
                fontWeight = FontWeight.Bold, textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(8.dp)
                    .weight(1f),
                color = Color.Black
            )
        }

    }
}