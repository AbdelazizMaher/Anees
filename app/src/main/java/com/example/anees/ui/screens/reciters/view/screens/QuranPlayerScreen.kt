package com.example.anees.ui.screens.reciters.view.screens

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.anees.R
import com.example.anees.data.model.RecitationModel
import com.example.anees.enums.SuraTypeEnum
import com.example.anees.services.RadioService
import com.example.anees.ui.screens.radio.components.ScreenBackground
import com.example.anees.ui.screens.reciters.view_model.QuranPlayerViewModel
import com.example.anees.utils.SharedModel
import com.example.anees.utils.media_helper.RadioPlayer

@Composable
fun QuranPlayerScreen(
    recitationModel: RecitationModel?,
    recitationName: String?,
    initialSuraIndex: Int = 0,
    isOnline: Boolean = true,
    onBackClick: () -> Unit = {}
) {
    val viewModel: QuranPlayerViewModel = viewModel()
    val currentTrack by viewModel.currentTrack.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(Unit) {
        if (isOnline) {
            viewModel.setOnlinePlaylist(recitationModel!!, recitationName!!)
            viewModel.playSura(initialSuraIndex)
        } else {
            viewModel.setOfflinePlaylist()
            viewModel.playSura(initialSuraIndex)
        }
    }


    DisposableEffect(lifecycleOwner) {
        val receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                if (SharedModel.isAppActive) {
                    onBackClick()
                } else {
                    (context as Activity).finishAffinity()
                }
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            context.registerReceiver(
                receiver,
                IntentFilter(RadioService.ACTION_CLOSE),
                Context.RECEIVER_NOT_EXPORTED
            )
        } else {
            ContextCompat.registerReceiver(
                context,
                receiver,
                IntentFilter(RadioService.ACTION_CLOSE),
                ContextCompat.RECEIVER_NOT_EXPORTED
            )
        }

        onDispose {
            context.unregisterReceiver(receiver)
            val stopIntent = Intent(context, RadioService::class.java)
            context.stopService(stopIntent)
        }
    }

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
        Box(modifier = Modifier
            .fillMaxSize()
        ) {
            ScreenBackground()
            IconButton(
                onClick = {
                    onBackClick()
                },
                modifier = Modifier
                    .padding(vertical = 24.dp)
                    .size(48.dp),
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Card(
                    shape = CircleShape,
                    modifier = Modifier.size(160.dp),
                ) {
                    if (isOnline) {
                        AsyncImage(
                            model = currentTrack?.reciterImage,
                            contentDescription = "صورة القارئ",
                            modifier = Modifier.fillMaxSize()
                        )
                    } else {
                        Image(
                            painter = painterResource(id = R.drawable.logo_foreground),
                            contentDescription = "صورة القارئ",
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = currentTrack?.reciter ?: "انيس",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (isOnline) {
                        Image(
                            painter = painterResource(id = if (currentTrack?.typeIcon == SuraTypeEnum.MECCA.name) R.drawable.kaaba else R.drawable.mosque),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                    }
                    Text(
                        text = "سُورَةٌ ${currentTrack?.title}",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Medium,
                        fontFamily = FontFamily(Font(R.font.othmani))
                    )
                }

                Text(
                    text = currentTrack?.description ?: "انيس",
                    fontSize = 16.sp,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(24.dp))

                Mp3Playback(
                    viewModel = viewModel,
                    isOnline = isOnline
                )
            }
        }
    }
}


@Composable
fun Mp3Playback(
    viewModel: QuranPlayerViewModel,
    isOnline: Boolean
) {
   val progress by viewModel.progress.collectAsStateWithLifecycle()
    val currentSuraIndex by viewModel.currentSuraIndex.collectAsStateWithLifecycle()
    val isPlaying by viewModel.isPlaying.collectAsStateWithLifecycle()
    val playlist by viewModel.playList.collectAsStateWithLifecycle()
    val rawDuration = RadioPlayer.getDuration()
    val duration = if (rawDuration > 0) rawDuration.toFloat() else 0f
    var sliderPosition by remember { mutableFloatStateOf(progress * duration) }
    val downloadingMap = remember { mutableStateMapOf<Int, Boolean>() }
    val isDownloading = downloadingMap[currentSuraIndex] ?: false

    LaunchedEffect(progress) {
        sliderPosition = progress * duration
    }

    LaunchedEffect(Unit) {
        viewModel.getNextSura()
    }

    Slider(
        value = sliderPosition.coerceIn(0f, duration),
        onValueChange = { newValue ->
            sliderPosition = newValue
        },
        onValueChangeFinished = {
            viewModel.seekTo(sliderPosition.toLong())

        },
        valueRange = 0f..maxOf(1f, duration),
        modifier = Modifier.fillMaxWidth(),
        colors = SliderDefaults.colors(
            thumbColor = Color(0xFF4CAF50),
            activeTrackColor = Color(0xFF4CAF50),
            inactiveTrackColor = Color(0xFFBDBDBD)
        )
    )

    Spacer(modifier = Modifier.height(32.dp))

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (currentSuraIndex > 0) {
            IconButton(onClick = {
                viewModel.onPrev()
            }) {
                Icon(
                    Icons.Default.SkipPrevious,
                    contentDescription = "السورة السابقة",
                    modifier = Modifier.size(48.dp),
                    tint = Color(0xFF4CAF50)
                )
            }
        } else {
            Spacer(modifier = Modifier.size(48.dp))
        }

        IconButton(onClick = {
            viewModel.playPauseSura()
        }) {
            Icon(
                imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                contentDescription = "تشغيل / إيقاف",
                modifier = Modifier.size(64.dp),
                tint = Color(0xFF4CAF50)
            )
        }

        if (currentSuraIndex < playlist.lastIndex) {
            IconButton(onClick = {
                viewModel.onNext()
            }) {
                Icon(
                    Icons.Default.SkipNext,
                    contentDescription = "السورة التالية",
                    modifier = Modifier.size(48.dp),
                    tint = Color(0xFF4CAF50)
                )
            }
        } else {
            Spacer(modifier = Modifier.size(48.dp))
        }
        if (isOnline) {
            IconButton(
                onClick = {
                    viewModel.downloadCurrentSura()
                    downloadingMap[currentSuraIndex] = true
                },
                enabled = !isDownloading
            ) {
                Icon(
                    Icons.Default.Download,
                    contentDescription = "تحميل",
                    modifier = Modifier.size(32.dp)
                        .alpha(if (isDownloading) 0.5f else 1f),
                    tint = Color(0xFF4CAF50)
                )
            }
        }
    }
}
