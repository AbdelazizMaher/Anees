package com.muslim.anees.ui.screens.azan.component

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.muslim.anees.enums.PrayEnum
import com.muslim.anees.utils.extensions.convertNumbersToArabic

@Preview
@Composable
fun AzanPulseView(
    prayerName: String = PrayEnum.FAJR.value,
    prayerTime: String = "5:37 ص".convertNumbersToArabic(),
    modifier: Modifier = Modifier,
    isDay: Boolean = false
) {
    val infiniteTransition = rememberInfiniteTransition()
    val pulse = infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.4f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1200, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(250.dp)
                .graphicsLayer {
                    scaleX = pulse.value
                    scaleY = pulse.value
                    alpha = 1.4f - pulse.value
                }
                .background(
                    color = Color.White.copy(alpha = 0.2f),
                    shape = CircleShape
                )
        )
        Box(
            modifier = Modifier
                .size(200.dp)
                .background(
                    color = Color.White.copy(alpha = 0.2f),
                    shape = CircleShape
                )
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = prayerTime,
                color = if(!isDay) Color.White else Color.Black.copy(alpha = 0.5f),
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = prayerName,
                color = if(!isDay) Color.White else Color.Black.copy(alpha = 0.5f),
                fontSize = 20.sp

            )

        }
    }
}