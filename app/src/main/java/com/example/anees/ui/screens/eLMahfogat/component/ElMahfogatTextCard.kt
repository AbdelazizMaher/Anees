package com.example.anees.ui.screens.eLMahfogat.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.anees.R

@Composable
fun ElMahfogatTextCard(textContent: String) {
    val highlightedText = buildAnnotatedString {
        val words = textContent.split(" ")
        for (word in words) {
            if (word.contains("الله")) {
                withStyle(style = SpanStyle(color = Color.Red)) {
                    append("$word ")
                }
            } else {
                withStyle(style = SpanStyle(color = Color(0xFF1A4D2E))) { // Dark green shade
                    append("$word ")
                }
            }
        }
    }

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .border(1.dp, Color(0xFF1A4D2E), RoundedCornerShape(16.dp)),
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(4.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFEFFBF1)) // light green background
        ) {
            Text(
                text = highlightedText,
                fontSize = 16.sp,
                color = Color(0xFF0B1D12),
                fontFamily = FontFamily(Font(R.font.othmani)),
                textAlign = TextAlign.Justify,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}
