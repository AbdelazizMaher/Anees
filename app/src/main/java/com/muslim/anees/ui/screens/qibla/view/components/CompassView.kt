package com.muslim.anees.ui.screens.qibla.view.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.muslim.anees.R

@Composable
fun CompassView(
    modifier: Modifier = Modifier,
    kaabaImageId: Int,
    deviceRotation: Float,
    qiblaRotation: Float
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.compassbg),
            contentDescription = "Qibla Compass Background",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.align(Alignment.Center)
        )
        Image(
            painter = painterResource(id = R.drawable.qiblaaa),
            contentDescription = "Qibla Compass Needle",
            modifier = Modifier
                .rotate(-deviceRotation)
                .align(Alignment.Center)
        )

        Box(
            modifier = Modifier
                .size(200.dp)
                .align(Alignment.Center)
                .rotate(qiblaRotation)

        ) {
            Image(
                painter = painterResource(id = kaabaImageId),
                contentDescription = "Kaaba Direction",
                modifier = Modifier
                    .size(32.dp)
                    .padding(top = 8.dp)
                    .align(Alignment.TopCenter)
            )
        }
    }
}