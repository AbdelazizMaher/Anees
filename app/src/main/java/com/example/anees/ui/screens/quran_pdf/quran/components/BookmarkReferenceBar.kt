package com.example.anees.ui.screens.quran_pdf.quran.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@Composable
fun VerticalBookmarkBar(
    modifier: Modifier = Modifier
        .padding(start = 16.dp)
        .width(24.dp)
        .height(84.dp)
    ,
    color: Color = Color(0xFF8A2BE2).copy(alpha = 0.6f),
    triangleHeight: Dp = 16.dp
) {

        Canvas(modifier = modifier) {
            val w = size.width
            val h = size.height
            val t = triangleHeight.toPx()

            // Build the bookmark shape path
            val path = Path().apply {
                moveTo(0f, 0f)            // top-left
                lineTo(w, 0f)             // top-right
                lineTo(w, h - t)          // right down to start of triangle
                lineTo(w / 2f, h)         // point of triangle
                lineTo(0f, h - t)         // left start of triangle
                close()                   // back to top-left
            }

            drawPath(path, color)
        }
}

@Preview(showBackground = true)
@Composable
fun PreviewVerticalBookmarkBar() {
    VerticalBookmarkBar()
}
