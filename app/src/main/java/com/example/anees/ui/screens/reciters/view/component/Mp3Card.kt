package com.example.anees.ui.screens.reciters.view.component

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.anees.R
import com.example.anees.ui.screens.tafsir.component.SurahNumber
import com.example.anees.utils.extensions.convertNumbersToArabic
import com.example.anees.utils.sura_mp3_helper.suraUrls

@Composable
fun Mp3Card(
    surah: Pair<String, String> = suraUrls[0],
    index: Int = 1,
    onClick: (index: Int) -> Unit
) {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color(0xEB803F0B), RoundedCornerShape(16.dp))
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(color = Color(0xFF6AB0AB)),
                    onClick = {
                        onClick(index-1)
                    }
                )
                .padding(horizontal = 12.dp, vertical = 12.dp)
        ) {
            Text(
                text = "سُورَةٌ ${surah.first}",
                fontSize = 22.sp,
                color = Color(0xFF311403),
                fontFamily = FontFamily(Font(R.font.othmani)),
                textAlign = TextAlign.Right,
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(8.dp))

            SurahNumber("$index".convertNumbersToArabic())
        }
    }

}