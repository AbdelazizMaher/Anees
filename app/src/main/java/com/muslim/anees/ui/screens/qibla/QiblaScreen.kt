package com.muslim.anees.ui.screens.qibla

import android.Manifest
import android.app.Activity
import android.content.Context
import android.os.Vibrator
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.muslim.anees.R
import com.muslim.anees.enums.AppPermission
import com.muslim.anees.ui.dialog.AneesAlertDialog
import com.muslim.anees.ui.screens.hadith.components.QiblaTitle
import com.muslim.anees.utils.location.LocationProvider

@Composable
fun QiblaScreen(
    onBackClick: () -> Unit = {}
) {
    val context = LocalContext.current
    val activity = context as Activity
    val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    val viewModel: QiblaViewModel = viewModel()
    var showNotificationPermissionDialog by remember { mutableStateOf(true) }

    val locationLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->

        if (granted) {
            showNotificationPermissionDialog = false
            LocationProvider(context).fetchLatLong() { location ->
                viewModel.updateQiblaDirection(location.latitude, location.longitude)
            }
        }
    }

    if (showNotificationPermissionDialog && !AppPermission.Location.isGranted(context)) {
        AneesAlertDialog(
            title = AppPermission.Location.title,
            message = AppPermission.Location.message,
            onConfirmLabel = "سماح",
            onConfirm = { locationLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION) },
            onDismiss = onBackClick
        )
    }

    val deviceAzimuth by viewModel.deviceAzimuth
    val bearingToQibla by viewModel.bearingToQibla

    val tolerance = 1f
    val isAligned = when {
        bearingToQibla in (0f..tolerance) || bearingToQibla in (360f - tolerance..360f) -> true
        bearingToQibla in (355f..360f) || bearingToQibla in (0f..5f) -> true
        else -> false
    }
    val kaabaImage = if (isAligned)
        R.drawable.kaaba2
    else
        R.drawable.kaaba_im

    LaunchedEffect(isAligned) {
        if (isAligned) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                vibrator.vibrate(
                    android.os.VibrationEffect.createOneShot(
                        500,
                        android.os.VibrationEffect.DEFAULT_AMPLITUDE
                    )
                )
            } else {
                vibrator.vibrate(500)
            }
        }
    }

    val animatedDeviceRotation by animateFloatAsState(
        targetValue = deviceAzimuth,
        animationSpec = tween(300, easing = LinearEasing)
    )

    val animatedQiblaRotation by animateFloatAsState(
        targetValue = bearingToQibla,
        animationSpec = tween(300, easing = LinearEasing)
    )

    Column(
        Modifier
            .fillMaxSize()
            .padding(top = 48.dp)
            .background(color = Color(0xFF272727)),

        ) {
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
            QiblaTitle(title = "القبلة", onBackClick = { onBackClick() }, size = 24)
        }
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.compassbg),
                contentDescription = "Qibla Compass Background",
                modifier = Modifier
                    .background(color = Color(0xFF272727))
                    .align(Alignment.Center)
            )

            Image(
                painter = painterResource(id = R.drawable.qiblaa),
                contentDescription = "Qibla Compass Needle",
                modifier = Modifier
                    .rotate(-animatedDeviceRotation)
                    .align(Alignment.Center)
            )

            Box(
                modifier = Modifier
                    .size(200.dp)
                    .align(Alignment.Center)
                    .rotate(animatedQiblaRotation)

            ) {
                Image(
                    painter = painterResource(id = kaabaImage),
                    contentDescription = "Kaaba Direction",
                    modifier = Modifier
                        .size(32.dp)
                        .padding(top = 8.dp)
                        .align(Alignment.TopCenter)
                )
            }
            val displayAngle = ((bearingToQibla % 360) + 360) % 360
            Text(
                text = "${displayAngle.toInt()}°",
                color = Color.White,
                style = TextStyle(fontSize = 20.sp),
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(top = 20.dp) // Adjust the position if needed
            )
        }
    }
}
