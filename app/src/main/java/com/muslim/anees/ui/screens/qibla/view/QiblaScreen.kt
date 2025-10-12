package com.muslim.anees.ui.screens.qibla.view

import android.Manifest
import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.batoulapps.adhan.Coordinates
import com.muslim.anees.R
import com.muslim.anees.enums.AppPermission
import com.muslim.anees.ui.dialog.AneesAlertDialog
import com.muslim.anees.ui.screens.hadith.components.ScreenTitle
import com.muslim.anees.ui.screens.qibla.view.components.CompassView
import com.muslim.anees.ui.screens.qibla.view.components.QiblaInfoCard
import com.muslim.anees.ui.screens.qibla.viewmodel.QiblaViewModel
import com.muslim.anees.ui.screens.radio.view.components.ScreenBackground
import com.muslim.anees.utils.location.LocationProvider

@Composable
fun QiblaScreen(
    viewModel: QiblaViewModel = hiltViewModel(),
    onBackClick: () -> Unit = {},
    coordinates: MutableState<Coordinates>
) {
    val context = LocalContext.current
    val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    var showNotificationPermissionDialog by remember { mutableStateOf(true) }

    val locationLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->

        if (granted) {
            showNotificationPermissionDialog = false
            LocationProvider(context).fetchLatLong() { location ->
                viewModel.updateQiblaDirection(location.latitude, location.longitude)
                coordinates.value = Coordinates(location.latitude, location.longitude)
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

    LaunchedEffect(Unit) {
        viewModel.updateQiblaDirection(coordinates.value.latitude, coordinates.value.longitude)
    }
    LaunchedEffect(isAligned) {
        if (isAligned) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(
                    VibrationEffect.createOneShot(
                        500,
                        VibrationEffect.DEFAULT_AMPLITUDE
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

    Box(Modifier.fillMaxSize()) {
        ScreenBackground()
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(top = 48.dp)
        ) {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                ScreenTitle(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    title = "القبلة",
                    onBackClick = { onBackClick() },
                    size = 24
                )
            }
            Spacer(Modifier.height(8.dp))
            CompassView(
                modifier = Modifier.fillMaxWidth(),
                kaabaImageId = kaabaImage,
                deviceRotation = animatedDeviceRotation,
                qiblaRotation = animatedQiblaRotation
            )
            Spacer(Modifier.height(8.dp))
            QiblaInfoCard(
                bearingToQibla = bearingToQibla,
            )
        }
    }
}






