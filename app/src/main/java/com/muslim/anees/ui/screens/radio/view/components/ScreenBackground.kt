package com.muslim.anees.ui.screens.radio.view.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.muslim.anees.R

@Composable
fun ScreenBackground(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(id = R.drawable.zekrback),
        contentDescription = "Background",
        modifier = modifier.fillMaxSize().alpha(0.22f),
        contentScale = ContentScale.Crop
    )
}