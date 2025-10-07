package com.muslim.anees.ui.screens.tafsir.component

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.muslim.anees.R
import com.muslim.anees.data.model.Result

@Composable
fun TafsirDeatilsCard(tafsir: Result) {
    val isExpanded = remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .border(1.dp, Color(0xEB803F0B), RoundedCornerShape(16.dp))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(color = Color(0xFF6AB0AB)),
                onClick = {
                    isExpanded.value = !isExpanded.value
                }
            )
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 300,
                    easing = FastOutSlowInEasing
                )
            )
    ) {
        Column {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd
            ) {
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "{ ${tafsir.arabic_text} }.",
                            fontSize = 18.sp,
                            color = Color(0xFF311403),
                            fontFamily = FontFamily(Font(R.font.othmani)),
                            textAlign = TextAlign.Justify,
                            modifier = Modifier.weight(1f)
                                .padding(end = 4.dp)

                        )

                        Spacer(modifier = Modifier.width(4.dp))

                        SurahNumber(tafsir.aya)
                    }
                }

            }


            Spacer(modifier = Modifier.height(8.dp))
            if (isExpanded.value) {

                val highlightedText = buildAnnotatedString {
                    val words = tafsir.translation.split(" ")
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
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(),
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        Text(
                            text = highlightedText,
                            fontSize = 14.sp,
                            color = Color(0xFF6F3A18),
                            fontFamily = FontFamily(Font(R.font.othmani)),
                            textAlign = TextAlign.Justify,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}