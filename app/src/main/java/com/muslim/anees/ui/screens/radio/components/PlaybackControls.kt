package com.muslim.anees.ui.screens.radio.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.muslim.anees.R
import com.muslim.anees.ui.screens.radio.RadioViewModel
import com.muslim.anees.utils.extensions.isInternetAvailable
import com.muslim.anees.utils.extensions.isVolumeZero

@Composable
fun PlaybackControls(
    isPlaying: Boolean,
    viewModel: RadioViewModel,
    onError: (String) -> Unit
) {
    val context = LocalContext.current

    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp)
            .offset(y = (-36).dp)
    ) {
        PlaybackButton(R.drawable.back) { viewModel.previousStation() }
        PlaybackButton(if (isPlaying) R.drawable.play else R.drawable.pause) {
            when {
                !context.isInternetAvailable() -> {
                    onError("لا يوجد اتصال بالإنترنت")
                }
                context.isVolumeZero() -> {
                    onError("رفع مستوى الصوت لتتمكن من الاستماع")
                }
                else -> {
                    viewModel.playPauseRadio()
                }
            }
        }
        PlaybackButton(R.drawable.right) { viewModel.nextStation() }
    }
}

@Composable
fun PlaybackButton(iconResId: Int,size:Int=72, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(size.dp)
            .clip(CircleShape)
            .clickable(onClick = onClick)
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = iconResId),
            contentDescription = null,
            modifier = Modifier.size(42.dp)
        )
    }
}