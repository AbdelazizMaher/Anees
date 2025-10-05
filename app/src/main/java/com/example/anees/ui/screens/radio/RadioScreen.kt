package com.example.anees.ui.screens.radio

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.anees.services.RadioService
import com.example.anees.ui.screens.hadith.components.ScreenTitle
import com.example.anees.ui.screens.radio.components.CustomSnackbar
import com.example.anees.ui.screens.radio.components.PlaybackControls
import com.example.anees.ui.screens.radio.components.ScreenBackground
import com.example.anees.ui.screens.radio.components.StationImageCard
import com.example.anees.ui.screens.radio.components.StationInfoCard
import com.example.anees.utils.SharedModel

@Composable
fun RadioScreen(viewModel: RadioViewModel = hiltViewModel(),navToHome: () -> Unit = {}) {
    val isPlaying by viewModel.isPlaying.collectAsStateWithLifecycle()
    val currentStation by viewModel.currentStation.collectAsStateWithLifecycle()
    val snackbarMessage = remember { mutableStateOf<String?>(null) }

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        val receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                if (SharedModel.isAppActive) {
                    navToHome()
                }else {
                    (context as Activity).finishAffinity()
                }
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            context.registerReceiver(receiver, IntentFilter(RadioService.ACTION_CLOSE), Context.RECEIVER_NOT_EXPORTED)
        }else {
            ContextCompat.registerReceiver(context, receiver, IntentFilter(RadioService.ACTION_CLOSE), ContextCompat.RECEIVER_NOT_EXPORTED)
        }

        onDispose {
            context.unregisterReceiver(receiver)
            val stopIntent = Intent(context, RadioService::class.java)
            context.stopService(stopIntent)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        ScreenBackground()

        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
            Column(
                modifier = Modifier.fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .padding(vertical = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                ScreenTitle( title = "محطة أنيس الإذاعية", onBackClick = {navToHome()}, size = 24)
                Spacer(modifier = Modifier.height(16.dp))
                StationImageCard(currentStation)
                Box(
                    modifier = Modifier.padding(horizontal = 12.dp).fillMaxWidth()
                ) {
                    StationInfoCard(currentStation)
                    PlaybackControls(
                        isPlaying = isPlaying,
                        viewModel = viewModel,
                        onError = { snackbarMessage.value = it }
                    )
                }
            }
        }

        snackbarMessage.value?.let {
            CustomSnackbar(message = it) {
                snackbarMessage.value = null
            }
        }
    }
}








