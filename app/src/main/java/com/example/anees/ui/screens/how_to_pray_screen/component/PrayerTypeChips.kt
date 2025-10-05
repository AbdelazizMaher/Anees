package com.example.anees.ui.screens.how_to_pray_screen.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.anees.utils.how_to_pray_helper.howToPrayChipsList


@Composable
fun HowToPrayChips(onStateChange:(String)-> Unit={}) {
    val list = howToPrayChipsList
    val defaultOption = howToPrayChipsList[0]
    var selectedOption by remember { mutableStateOf(defaultOption) }

    Row(
        Modifier
            .padding(horizontal = 8.dp)
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        list.forEach {
            val isSelected = it == selectedOption
            val chipColor = if (isSelected) Color(0xEB803F0B) else Color.White
            FilterChip(
                selected = isSelected,
                onClick = {
                    selectedOption = it
                    onStateChange(it)
                },
                label = {
                    Text(it)
                },
                leadingIcon = if (isSelected) {
                    {
                        Icon(
                            imageVector = Icons.Filled.Done,
                            contentDescription = "Done icon",
                            modifier = Modifier.size(FilterChipDefaults.IconSize)
                        )
                    }
                } else null,
                colors= FilterChipDefaults.filterChipColors(
                    selectedContainerColor = chipColor,
                    selectedLabelColor = Color.White,

                ),
                border = BorderStroke(1.dp, Color(0xEB803F0B))
            )
            Spacer(Modifier.padding(horizontal = 4.dp))
        }
    }
}

