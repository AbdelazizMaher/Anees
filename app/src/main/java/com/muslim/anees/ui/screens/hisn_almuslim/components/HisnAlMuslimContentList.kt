package com.muslim.anees.ui.screens.hisn_almuslim.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HisnAlMuslimContentList(
    titlesWithTexts: List<Pair<String, List<String>>>,
    expandedStates: MutableMap<String, Boolean>
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        titlesWithTexts.forEach { (title, texts) ->
            item {
                TitleCard(
                    title = title,
                    isExpanded = expandedStates[title] == true,
                    onToggleExpand = {
                        expandedStates[title] = !(expandedStates[title] ?: false)
                    }
                )
            }

            if (expandedStates[title] == true) {
                items(texts.size) { index ->
                    HisnAlMuslimTextCard(textContent = texts[index])
                }
            }
        }
        item {
            Spacer(modifier = Modifier.height(48.dp))
        }
    }
}