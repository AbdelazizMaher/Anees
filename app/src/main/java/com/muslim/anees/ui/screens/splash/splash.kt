package com.muslim.anees.ui.screens.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.RenderMode
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.muslim.anees.R
import kotlinx.coroutines.delay


@Composable
fun SplashScreen(navToHome: () -> Unit
) {
    val composition = rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.splash))

    LaunchedEffect(true) {
        delay(2000)
        navToHome()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFe8e0d5)),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(R.drawable.anees),
            contentDescription = null,
            modifier = Modifier
                .size(300.dp)
                .align(Alignment.Center),
            colorFilter = ColorFilter.tint(Color(0xFF6F3A18))
        )

        LottieAnimation(
            composition = composition.value,
            iterations = LottieConstants.IterateForever,
            renderMode = RenderMode.AUTOMATIC,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
                .offset(y = (-150).dp)
        )
    }

}