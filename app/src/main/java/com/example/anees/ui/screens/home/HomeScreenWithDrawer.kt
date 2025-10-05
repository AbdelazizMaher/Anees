package com.example.anees.ui.screens.home

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateOffset
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.batoulapps.adhan.Coordinates
import com.example.anees.R
import com.example.anees.ui.dialog.OverlayBlocker
import com.example.anees.ui.navigation.ScreenRoute
import com.example.anees.ui.screens.home.component.HomeDrawer
import com.example.anees.ui.screens.radio.components.ScreenBackground
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenWithDrawer(
    location: MutableState<Coordinates>,
    navToSebiha: () -> Unit = {},
    navToQibla: () -> Unit = {},
    navToQuran: () -> Unit = {},
    navToAzkar: () -> Unit = {},
    navToHadith: () -> Unit = {},
    navToRadio: () -> Unit,
    navToTafsir: () -> Unit,
    navToPrayer: () -> Unit = {},
    navToReciters: () -> Unit = {},
    navToNamesOfAllah: () -> Unit = {},
    navToSettings: () -> Unit = {},
    viewModel: HomeViewModel = viewModel(),
    navToHisnAlMuslim: () -> Unit = {},
    navToPrayScreen: () -> Unit,
    navToElMahfogat: () -> Unit,
    isSyncing: MutableState<Boolean>,
) {
    val drawerState = remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(true) }
    val notificationLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { _ ->
        showDialog = false
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFC9C0B3))
    )
    {
        ScreenBackground()
        HomeDrawer(
            isOpen = drawerState.value,
            onNavigate = { destination ->
                scope.launch {
                    drawerState.value = false
                    when (destination) {
                        ScreenRoute.HomeScreen -> {}
                        ScreenRoute.QiblaScreen -> navToQibla()
                        ScreenRoute.AdhkarScreen -> navToAzkar()
                        ScreenRoute.NamesOfAllahScreen -> navToNamesOfAllah()
                        ScreenRoute.RadioScreen -> navToRadio()
                        ScreenRoute.TafsirScreen -> navToTafsir()
                        ScreenRoute.RecitersScreen -> navToReciters()
                        ScreenRoute.Sebiha -> navToSebiha()
                        ScreenRoute.CompleteQuranScreen -> navToQuran()
                        ScreenRoute.HadithAuthorsScreen -> navToHadith()
                        ScreenRoute.PrayerTimesScreen -> navToPrayer()
                        ScreenRoute.HisnAlMuslimScreen -> navToHisnAlMuslim()
                        ScreenRoute.SettingsScreen -> navToSettings()
                        else -> {}
                    }
                }
            }
        )

        val currentState = if (drawerState.value) MenuState.EXPANDED else MenuState.COLLAPSED
        val transition = updateTransition(currentState, label = "menuTransition")

        val scale by transition.animateFloat(
            transitionSpec = { tween(300, easing = LinearOutSlowInEasing) },
            label = "scale"
        ) { state ->
            when (state) {
                MenuState.EXPANDED -> 0.7f
                MenuState.COLLAPSED -> 1f
            }
        }

        val offset by transition.animateOffset(
            transitionSpec = { tween(300, easing = LinearOutSlowInEasing) },
            label = "offset"
        ) { state ->
            when (state) {
                MenuState.EXPANDED -> Offset(-450f, 0f)
                MenuState.COLLAPSED -> Offset(0f, 0f)
            }
        }

        val roundness by transition.animateDp(
            transitionSpec = { tween(300) },
            label = "roundness"
        ) { state ->
            when (state) {
                MenuState.EXPANDED -> 20.dp
                MenuState.COLLAPSED -> 0.dp
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                    translationX = offset.x
                }
                .clip(RoundedCornerShape(roundness))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFC9C0B3))
            ) {

                Image(
                    painter = painterResource(R.drawable.homeback),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .alpha(0.22f),
                    contentScale = ContentScale.Crop
                )
                Column {
                    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {

                        TopAppBar(
                            colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
                            title = {
                                Text(
                                    text = "أنيس المسلم",
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.fillMaxWidth(),
                                    fontFamily = FontFamily(Font(R.font.othmani)),

                                    textAlign = TextAlign.End
                                )
                            },
                            actions = {
                                IconButton(onClick = {
                                    drawerState.value = !drawerState.value
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.Menu,
                                        contentDescription = "القائمة"
                                    )
                                }
                            },
                            navigationIcon = {}
                        )
                    }
                    HomeScreen(
                        isSyncing = isSyncing,
                        location = location,
                        navToSebiha = navToSebiha,
                        navToQibla = navToQibla,
                        navToQuran = navToQuran,
                        navToAzkar = navToAzkar,
                        navToHadith = navToHadith,
                        navToRadio = navToRadio,
                        navToTafsir = navToTafsir,
                        navToPrayer = navToPrayer,
                        navToReciters = navToReciters,
                        navToNamesOfAllah = navToNamesOfAllah,
                        navToHisnAlMuslim = navToHisnAlMuslim,
                        navToPrayScreen = navToPrayScreen,
                        navToElMahfogat = navToElMahfogat,
                    )
                }

                if (isSyncing.value) OverlayBlocker()
            }
        }

    }
}


enum class MenuState {
    EXPANDED, COLLAPSED
}