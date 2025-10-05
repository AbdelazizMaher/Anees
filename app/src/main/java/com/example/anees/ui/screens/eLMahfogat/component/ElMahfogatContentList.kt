package com.example.anees.ui.screens.eLMahfogat.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.anees.ui.screens.hisn_almuslim.components.TitleCard

@Composable
fun ElMahfogatContentList(
    titlesWithTexts: List<Pair<String, List<String>>>,
    expandedStates: MutableMap<String, Boolean>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        titlesWithTexts.forEach { (title, texts) ->

            TitleCard(
                title = title,
                isExpanded = expandedStates[title] == true,
                onToggleExpand = {
                    expandedStates[title] = !(expandedStates[title] ?: false)
                }
            )

            if (expandedStates[title] == true) {
                texts.forEach { text ->
                    ElMahfogatTextCard(textContent = text)
                }
            }
        }

        Spacer(modifier = Modifier.height(48.dp))
    }
}
