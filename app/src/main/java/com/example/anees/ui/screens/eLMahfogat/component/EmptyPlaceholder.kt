package com.example.anees.ui.screens.eLMahfogat.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun EmptyPlaceholder(
    message: String = "لا يوجد عناصر محفوظة",
    height: Int = 60
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = message,
            color = Color.DarkGray,
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}
