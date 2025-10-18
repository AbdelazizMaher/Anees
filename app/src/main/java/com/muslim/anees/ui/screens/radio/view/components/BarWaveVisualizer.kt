package com.muslim.anees.ui.screens.radio.view.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.dp
import kotlin.math.sin


@Composable
fun BarWaveVisualizer() {
    val infiniteTransition = rememberInfiniteTransition(label = "waveTransition")
    val waveShift by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 3000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ), label = "waveShift"
    )

    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
    ) {
        val waveHeight = 20f
        val waveLength = size.width / 1.5f

        val path = Path()

        path.moveTo(0f, size.height / 2)

        val points = 50
        for (i in 0..points) {
            val x = i * size.width / points
            val angle = (i.toFloat() / points * 360f + waveShift) * (Math.PI / 180f)
            val y = (size.height / 2) + (waveHeight * sin(angle)).toFloat()
            path.lineTo(x, y)
        }

        path.lineTo(size.width, size.height)
        path.lineTo(0f, size.height)
        path.close()

        drawPath(
            path = path,
            brush = Brush.verticalGradient(
                colors = listOf(Color(0xFF9E292E), Color(0xFF181818))
            )
        )
    }
}