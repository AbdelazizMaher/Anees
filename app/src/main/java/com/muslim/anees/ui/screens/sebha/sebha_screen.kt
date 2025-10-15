package com.muslim.anees.ui.screens.sebha

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.SwapVert
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.RenderMode
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.muslim.anees.R
import com.muslim.anees.data.model.Sebiha
import com.muslim.anees.ui.screens.hadith.components.ScreenTitle
import com.muslim.anees.ui.screens.radio.components.ScreenBackground
import com.muslim.anees.ui.screens.sebha.component.AddZekrDialog
import com.muslim.anees.ui.screens.sebha.component.AzkarButtomSheet
import com.muslim.anees.utils.extensions.convertNumbersToArabic
import kotlinx.coroutines.delay

@Composable
fun SebihaScreen(
    navToHome: () -> Unit = {}
) {
    val viewModel: SebihaViewModel = hiltViewModel()
    val sebiha = viewModel.sebiha.collectAsState()
    var isLottieVisible by remember { mutableStateOf(false) }
    val rounds = sebiha.value.rounds
    val composition = rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.celebration))
    LaunchedEffect(rounds) {
        if (rounds % 2 == 0 && rounds != 0) {
            isLottieVisible = true
            delay(3000)
            isLottieVisible = false
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .zIndex(2f)
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFFF5F0E1), Color(0xFFEAE3D2))
                )
            )
            .padding(horizontal = 16.dp)
    ) {
        if (isLottieVisible) {
            LottieAnimation(
                composition = composition.value,
                iterations = LottieConstants.IterateForever,
                modifier = Modifier
                    .fillMaxSize()
                    .zIndex(1f),
                renderMode = RenderMode.AUTOMATIC
            )
        }

        SebhaMainScreen(sebiha, viewModel, navToHome, isLottieVisible)


    }
}


@Composable
fun SebhaMainScreen(
    sebiha: State<Sebiha>,
    viewModel: SebihaViewModel,
    navToHome: () -> Unit = {},
    isLottieVisible: Boolean
) {
    var showBottomSheet by remember { mutableStateOf(false) }
    val showAddZekrDialog = remember { mutableStateOf(false) }

    LaunchedEffect(showBottomSheet) {
        viewModel.getAllAzkarFromDb()
    }

    var counter = sebiha.value.count
    var rounds = sebiha.value.rounds
    var totalRounds = sebiha.value.totalRounds
    var mainZekir = sebiha.value.name

    Box(Modifier.fillMaxSize()) {
        ScreenBackground()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 24.dp),
        ) {

            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                ScreenTitle(title = "السبحة", onBackClick = navToHome, size = 24)

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 8.dp)
                ) {

                    Text(
                        text = mainZekir,
                        color = Color.Black,
                        fontSize = 24.sp,
                        fontFamily = FontFamily(Font(R.font.othmani)),
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    )

                    Spacer(Modifier.height(24.dp))
                    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text("إجمالي الدورات:  ", color = Color.Black, fontSize = 20.sp)
                                    Text(
                                        text = totalRounds.toString().convertNumbersToArabic(),
                                        color = Color(0xFF29CA6A),
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 22.sp
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    if (!isLottieVisible) {
                                        IconButton(
                                            onClick = {
                                                totalRounds = 0
                                                rounds = 0
                                                viewModel.addSebiha(
                                                    Sebiha(
                                                        0,
                                                        counter,
                                                        rounds,
                                                        totalRounds,
                                                        mainZekir
                                                    )
                                                )
                                            },
                                            modifier = Modifier
                                                .size(32.dp)
                                                .background(Color.Transparent, shape = CircleShape)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Refresh,
                                                contentDescription = null,
                                                tint = Color.Black
                                            )
                                        }
                                    }
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text("الدورات:  ", color = Color.Black, fontSize = 20.sp)
                                    Text(
                                        text = rounds.toString().convertNumbersToArabic(),
                                        color = Color(0xFF29CA6A),
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 22.sp
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    if (!isLottieVisible) {
                                        IconButton(
                                            onClick = {
                                                rounds = 0
                                                counter = 0

                                                viewModel.addSebiha(
                                                    Sebiha(
                                                        0,
                                                        counter,
                                                        rounds,
                                                        totalRounds,
                                                        mainZekir
                                                    )
                                                )
                                            },
                                            modifier = Modifier
                                                .size(32.dp)
                                                .background(Color.Transparent, shape = CircleShape)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Refresh,
                                                contentDescription = null,
                                                tint = Color.Black
                                            )
                                        }
                                    }
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text("التسبيح: ", color = Color.Black, fontSize = 20.sp)
                                    Text(
                                        text = "33/$counter".convertNumbersToArabic(),
                                        color = Color(0xFF29CA6A),
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 22.sp
                                    )
                                }
                            }

                            Box(
                                modifier = Modifier.size(52.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Image(
                                    painter = painterResource(R.drawable.star),
                                    contentDescription = null,
                                    modifier = Modifier.fillMaxSize()
                                )
                                IconButton(
                                    onClick = { showBottomSheet = true },
                                    modifier = Modifier
                                        .size(36.dp)
                                        .background(Color.Transparent, shape = CircleShape)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.SwapVert,
                                        contentDescription = null,
                                        tint = Color.Black
                                    )
                                }
                            }
                        }
                    }


                    Spacer(modifier = Modifier.height(4.dp))

                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(400.dp)
                    ) {
                        Image(
                            painter = painterResource(R.drawable.sebha),
                            contentDescription = null,
                            modifier = Modifier.size(300.dp)
                        )

                        Box(
                            modifier = Modifier
                                .size(100.dp)
                                .offset(y = 80.dp)
                                .background(Color.Transparent, shape = CircleShape)
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = rememberRipple(bounded = true, radius = 50.dp),
                                    onClick = {
                                        counter++
                                        if (counter > 33) {
                                            counter = 0
                                            rounds++
                                            totalRounds++
                                        }
                                        viewModel.addSebiha(
                                            Sebiha(
                                                0,
                                                counter,
                                                rounds,
                                                totalRounds,
                                                mainZekir
                                            )
                                        )
                                    }

                                )
                        )

                        Box(
                            modifier = Modifier
                                .size(30.dp)
                                .offset(x = 60.dp, y = 18.dp)
                                .background(Color.Transparent, shape = CircleShape)
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = rememberRipple(bounded = true, radius = 15.dp),
                                    onClick = {
                                        counter = 0

                                        viewModel.addSebiha(
                                            Sebiha(
                                                0,
                                                counter,
                                                rounds,
                                                totalRounds,
                                                mainZekir
                                            )
                                        )
                                    }
                                )
                        )

                        Text(
                            text = "$counter",
                            color = Color(0xFF1A8642),
                            fontSize = 30.sp,
                            fontFamily = FontFamily(
                                Font(R.font.degital),
                                Font(R.font.degital, FontWeight.Bold)
                            ),
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.offset(y = (-64).dp)
                        )
                    }

                }
            }

        }
    }
    if (showBottomSheet) {
        AzkarButtomSheet(
            viewModel =viewModel,
            showAddZekrDialog = showAddZekrDialog,
            mainZekir,
            onClose = { showBottomSheet = false }) {
            mainZekir = it
            rounds = 0
            counter = 0
            viewModel.addSebiha(
                Sebiha(
                    0,
                    counter,
                    rounds,
                    totalRounds = sebiha.value.totalRounds,
                    mainZekir
                )
            )
        }
    }
    if (showAddZekrDialog.value) {
        AddZekrDialog(
            viewModel= viewModel,
            showAddZekrDialog = showAddZekrDialog
        )
    }

}


