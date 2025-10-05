package com.example.anees.ui.screens.hadith.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.anees.data.model.EditionResponse
import com.example.anees.data.model.HadithEntity
import com.example.anees.ui.screens.hadith.getHadithsForRange

@Composable
fun DisplayHadiths(
    allHadiths: List<EditionResponse.Hadith>,
    hadithRange: Pair<Double, Double>,
    savedHadith: List<HadithEntity>,
    onToggleSave: (String) -> Unit
) {
    val hadiths = getHadithsForRange(allHadiths, hadithRange.first, hadithRange.second)

    LazyColumn(
        contentPadding = PaddingValues(vertical = 16.dp, horizontal = 4.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(hadiths) { hadith ->
            HadithCard(
                hadith,
                isSaved = savedHadith.any { it.title == hadith },
                onSaveClick  = { onToggleSave(hadith) }
            )
        }
        item {
            Spacer(modifier = Modifier.height(48.dp))
        }
    }
}
