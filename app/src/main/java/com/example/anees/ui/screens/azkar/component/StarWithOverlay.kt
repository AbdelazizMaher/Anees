package com.example.anees.ui.screens.azkar.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.anees.R

@Composable
fun StarWithOverlay(isSaved: Boolean, onStarClick: () -> Unit) {
    Box(
        modifier = Modifier
            .wrapContentSize()
            .clickable { onStarClick() }
    ) {
        Image(
            painter = painterResource(id = R.drawable.star),
            contentDescription = null,
            modifier = Modifier.size(36.dp)
        )

        if (isSaved) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .graphicsLayer(alpha = 0.5f)
                    .background(Color(0xFFA9745B), CircleShape)
            )
        }
    }
}
