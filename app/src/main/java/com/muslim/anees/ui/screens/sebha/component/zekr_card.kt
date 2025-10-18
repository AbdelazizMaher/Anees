package com.muslim.anees.ui.screens.sebha.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.muslim.anees.R

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ZekirSheetCard(
    zekir: String,
    isSelected: Boolean,
    onLongClick: ()-> Unit = {},
    onZekirClick: (String) -> Unit,
    ){
    val backgroundColor = if (isSelected) Color(0xFFe3fff0) else Color(0xFFe9e9d1)
    val textColor = if (isSelected) Color(0xFF62cc91) else Color(0xFF2E2E2E)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 8.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(backgroundColor )
            .border(1.dp, Color.White, RoundedCornerShape(16.dp))
            .combinedClickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(color = Color(0xFF62cc91)),
                onClick = { onZekirClick(zekir) },
                onLongClick = { onLongClick() }
            )
            .padding( vertical = 8.dp),

    ) {
        Text(
            text = zekir,
            fontSize = 20.sp,
            color = textColor,
            textAlign = TextAlign.Center,
            fontFamily = FontFamily(Font(R.font.othmani)),
            modifier = Modifier.fillMaxWidth().padding( 8.dp)
        )

    }
}