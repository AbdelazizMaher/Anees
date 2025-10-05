package com.example.anees.ui.screens.hadith.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import com.example.anees.ui.screens.azkar.component.StarWithOverlay

@Composable
fun HadithCard(
    hadithText: String,
    isSaved: Boolean = false,
    onSaveClick: (() -> Unit)? = null
) {

    val highlightedText = buildAnnotatedString {
        val words = hadithText.replace("<br>", "\n").split(" ")
        for (word in words) {
            if (word.contains("الله")) {
                withStyle(style = SpanStyle(color = Color.Red)) {
                    append("$word ")
                }
            } else {
                withStyle(style = SpanStyle(color = Color(0xFF6F3A18))) {
                    append("$word ")
                }
            }
        }
    }

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
                .border(1.dp, Color(0xEB803F0B), RoundedCornerShape(16.dp)),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)

            ) {
                Text(
                    text = highlightedText,
                    fontSize = 16.sp,
                    color = Color(0xFF281102),
                    fontFamily = FontFamily(Font(R.font.othmani)),
                    textAlign = TextAlign.Justify,
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.End,
            ) {
                if (onSaveClick != null) {
                    StarWithOverlay(isSaved = isSaved, onStarClick = onSaveClick)
                }
            }
        }
    }
}