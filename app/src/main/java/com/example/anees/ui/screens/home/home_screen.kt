package com.example.anees.ui.screens.home

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.batoulapps.adhan.Coordinates
import com.example.anees.R
import com.example.anees.ui.screens.home.component.ComponentCard
import com.example.anees.ui.screens.home.component.PrayerCardWithTimer
import com.example.anees.ui.screens.home.component.QuranCard
import com.example.anees.ui.screens.home.component.SubCards
import com.example.anees.utils.location.checkPermission
import com.example.anees.utils.location.handleLocationPermission
import com.example.anees.utils.location.isLocationEnabled


@Composable
fun HomeScreen(
    location: MutableState<Coordinates>,
    navToSebiha: () -> Unit = {},
    navToQibla: () -> Unit = {},
    navToQuran: () -> Unit = {},
    navToAzkar: () -> Unit = {},
    navToHadith: () -> Unit = {},
    navToRadio: () -> Unit = {},
    navToTafsir: () -> Unit = {},
    navToPrayer: () -> Unit = {},
    navToReciters: () -> Unit = {},
    navToNamesOfAllah: () -> Unit = {},
    navToHisnAlMuslim: () -> Unit = {},
    navToPrayScreen: () -> Unit = {},
    navToElMahfogat: () -> Unit = {}
) {

    val ctx = LocalContext.current
    val activity = ctx as Activity
    Box {
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .background(Color.Transparent)
            ) {
                PrayerCardWithTimer(location) {
                    if (ctx.checkPermission() && ctx.isLocationEnabled()) {
                        navToPrayer()
                    } else {
                        ctx.handleLocationPermission(activity)

                    }
                }
                SubCards(
                    navToSebiha = navToSebiha,
                    navToQibla = navToQibla,
                    navToRadio = navToRadio
                )
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.padding(horizontal = 24.dp)
                ) {
                    Text(
                        text = "الأقسام الرئيسية",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.End,
                        color = Color.Black,
                        fontFamily = FontFamily(Font(R.font.othmani)),

                        modifier = Modifier.fillMaxWidth()
                    )
                }

                QuranCard(onClick = navToQuran)
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    ComponentCard(
                        size = .45f,
                        title = "الحديث",
                        onClick = navToHadith,
                        id = R.drawable.had
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    ComponentCard(
                        size = .45f,
                        title = "التفسير",
                        onClick = navToTafsir,
                        id = R.drawable.taf
                    )

                }
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    ComponentCard(
                        size = .45f,
                        title = "القراء",
                        onClick = navToReciters,
                        id = R.drawable.sound
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    ComponentCard(
                        size = .45f,
                        title = "الأذكار",
                        onClick = navToAzkar,
                        id = R.drawable.do3aa
                    )

                }
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    ComponentCard(
                        size = .45f,
                        title = "حصن المسلم",
                        onClick = navToHisnAlMuslim,
                        id = R.drawable.hisen
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    ComponentCard(
                        size = .45f,
                        title = "كيف أصلي؟",
                        onClick = navToPrayScreen,
                        id = R.drawable.prayer_icon
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    ComponentCard(
                        size = .45f,
                        title = "المحفوظات",
                        onClick = navToElMahfogat,
                        id = R.drawable.downloaded_audio_card_icon
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    ComponentCard(
                        size = .45f,
                        title = "اسماء الله الحسنى",
                        onClick = navToNamesOfAllah,
                        id = R.drawable.allah
                    )
                }

                Spacer(modifier = Modifier.height(48.dp))
            }
        }
    }

}




