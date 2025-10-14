package com.muslim.anees.ui.screens.radio.view

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.SubcomposeAsyncImage
import com.muslim.anees.R
import com.muslim.anees.data.model.audio.AudioTrack
import com.muslim.anees.data.model.radio.audioStations
import com.muslim.anees.services.RadioService
import com.muslim.anees.ui.screens.hadith.components.ScreenTitle
import com.muslim.anees.ui.screens.radio.view.components.CustomSnackbar
import com.muslim.anees.ui.screens.radio.view.components.PlaybackControls
import com.muslim.anees.ui.screens.radio.view.components.ScreenBackground
import com.muslim.anees.ui.screens.radio.view.components.StationImageCard
import com.muslim.anees.ui.screens.radio.view.components.StationInfoCard
import com.muslim.anees.ui.screens.radio.viewmodel.RadioViewModel
import com.muslim.anees.utils.SharedModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RadioScreen(
    viewModel: RadioViewModel = hiltViewModel(),
    onBackClick: () -> Unit = {}
) {
    val isPlaying by viewModel.isPlaying.collectAsStateWithLifecycle()
    val currentStation by viewModel.currentStation.collectAsStateWithLifecycle()
    val snackbarMessage = remember { mutableStateOf<String?>(null) }
    var isBottomSheetVisible by remember { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

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

    Box(modifier = Modifier.fillMaxSize()) {
        ScreenBackground()

        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .padding(vertical = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                ScreenTitle(
                    title = "محطة أنيس الإذاعية",
                    onBackClick = { onBackClick() },
                    onClick = { isBottomSheetVisible = true },
                    size = 22
                )
                Spacer(modifier = Modifier.height(16.dp))
                StationImageCard(currentStation)
                Box(
                    modifier = Modifier
                        .padding(horizontal = 12.dp)
                        .fillMaxWidth()
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

        if (isBottomSheetVisible) {
            RadioBottomSheet(
                bottomSheetState = bottomSheetState,
                //currentStation = currentStation.,
                onDismiss = { isBottomSheetVisible = false }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RadioBottomSheet(
    bottomSheetState: SheetState,
    currentStationIndex: Int = 0,
    onDismiss: () -> Unit = {},
) {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        ModalBottomSheet(
            onDismissRequest = { onDismiss() },
            sheetState = bottomSheetState,
            containerColor = Color.White,
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(LocalConfiguration.current.screenHeightDp.dp * 0.6f)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                ) {
                    Text(
                        text = "محطات الراديو المتاحة",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = FontFamily(Font(R.font.othmani)),
                        modifier = Modifier.align(Alignment.Start)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "اختر محطة لبدء التشغيل",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal,
                        fontFamily = FontFamily(Font(R.font.othmani)),
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        contentPadding = PaddingValues(bottom = 24.dp)
                    ) {
                        items(
                            items = audioStations,
                            key = { it.index }
                        ) { station ->
                            RadioStationItem(
                                station = station,
                                isSelected = station.index == currentStationIndex,
                                onItemClick = { index ->

                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RadioStationItem(
    station: AudioTrack,
    isSelected: Boolean = false,
    onItemClick: (Int) -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
            .clickable { onItemClick(station.index) },
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) Color(0xFF121212) else Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = if (isSelected) 6.dp else 0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SubcomposeAsyncImage(
                model = station.reciterImage,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(8.dp)),
                loading = {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            color = Color.Black,
                            strokeWidth = 2.dp,
                            modifier = Modifier.size(48.dp)
                        )
                    }
                },
                error = {
                    Image(
                        painter = painterResource(id = R.drawable.anees),
                        contentDescription = null,
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(bottom = 32.dp)
                    )
                }
            )
            Spacer(modifier = Modifier.size(16.dp))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = station.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = if (isSelected) Color.White else Color(0xFF4E342E),
                    fontFamily = FontFamily(Font(R.font.othmani)),
                )
                Text(
                    text = station.description,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    color = if (isSelected) Color(0xFFBCAAA4) else Color(0xFF803F0B),
                    fontFamily = FontFamily(Font(R.font.othmani)),
                    maxLines = if (isSelected) Int.MAX_VALUE else 2,
                    overflow = if (isSelected) TextOverflow.Clip else TextOverflow.Ellipsis,
                    modifier = if (isSelected) Modifier.basicMarquee() else Modifier
                )
            }
            Spacer(modifier = Modifier.size(4.dp))
            AnimatedVisibility(
                visible = isSelected,
                enter = fadeIn(animationSpec = tween(400)),
                exit = fadeOut(animationSpec = tween(400))
            ) {
                PlayingAnimation()
            }
        }
    }
}

@Composable
fun PlayingAnimation() {
    val infiniteTransition = rememberInfiniteTransition(label = "equalizer")

    val height1 by infiniteTransition.animateFloat(
        initialValue = 14f,
        targetValue = 20f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1500, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ), label = "bar1"
    )

    val height2 by infiniteTransition.animateFloat(
        initialValue = 10f,
        targetValue = 24f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1800, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ), label = "bar2"
    )

    val height3 by infiniteTransition.animateFloat(
        initialValue = 16f,
        targetValue = 22f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ), label = "bar3"
    )

    Row(
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.spacedBy(3.dp),
        modifier = Modifier
            .height(24.dp)
            .width(18.dp)
    ) {
        Box(
            Modifier
                .width(3.dp)
                .height(height1.dp)
                .background(Color.White, RoundedCornerShape(2.dp))
        )
        Box(
            Modifier
                .width(3.dp)
                .height(height2.dp)
                .background(Color.White, RoundedCornerShape(2.dp))
        )
        Box(
            Modifier
                .width(3.dp)
                .height(height3.dp)
                .background(Color.White, RoundedCornerShape(2.dp))
        )
    }
}








