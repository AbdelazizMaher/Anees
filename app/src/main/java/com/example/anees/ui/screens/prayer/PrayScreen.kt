package com.example.anees.ui.screens.prayer

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.batoulapps.adhan.Coordinates
import com.example.anees.ui.screens.prayer.component.DateSection
import com.example.anees.ui.screens.prayer.component.HeaderWithTimer
import com.example.anees.ui.screens.prayer.component.PrayerList
import com.example.anees.ui.screens.prayer.component.PrayerTopBar
import com.example.anees.ui.screens.radio.components.ScreenBackground
import com.example.anees.utils.date_helper.DateHelper
import com.google.accompanist.systemuicontroller.rememberSystemUiController


@Composable
fun PrayerScreen(
    location: MutableState<Coordinates>,
    onPreviewClick: () -> Unit = {}, onBackClick: () -> Unit = {}){

    val systemUiController = rememberSystemUiController()
    val  context = LocalContext.current
//    var location by remember { mutableStateOf("") }


 /*   LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            val latitude = SharedPreferencesImpl(context).fetchData("latitude", 30.033333)
            val longitude = SharedPreferencesImpl(context).fetchData("longitude", 31.233334)
            val result = context.getCityAndCountryInArabic(latitude, longitude)

            withContext(Dispatchers.Main) {
                location = result
            }
        }
    }*/

    SideEffect {
        systemUiController.setStatusBarColor(
            color = Color.Transparent,
            darkIcons = true
        )
    }

    ScreenBackground()
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Box(
            Modifier.fillMaxSize()
                .padding(top = 24.dp)
        ){
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                ,horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                PrayerTopBar(location =
                    context.getCityAndCountryInArabic(location.value.latitude, location.value.longitude)
                ){
                    onBackClick()
                }
                Spacer(modifier = Modifier.height(32.dp))
                HeaderWithTimer(
                    onPreviewClick = onPreviewClick
                )
                Spacer(modifier = Modifier.height(32.dp))
                DateSection(DateHelper.getTodayHijriDate())
                Spacer(modifier = Modifier.height(8.dp))
                PrayerList()
            }
        }
    }
}

