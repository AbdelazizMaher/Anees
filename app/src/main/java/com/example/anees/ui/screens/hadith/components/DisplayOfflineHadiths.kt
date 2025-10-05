package com.example.anees.ui.screens.hadith.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.example.anees.data.model.EditionResponse

@Composable
fun DisplayOfflineHadiths(allHadiths: List<EditionResponse.Hadith>) {
    val hadiths = allHadiths.map { it.text }

    LazyColumn(
        contentPadding = PaddingValues(vertical = 16.dp, horizontal = 4.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(hadiths) { hadith ->
            HadithCard(hadith)
        }
    }
}