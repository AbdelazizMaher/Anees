package com.example.anees.ui.screens.home.component

import android.Manifest
import android.content.Context
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.batoulapps.adhan.Coordinates
import com.example.anees.R
import com.example.anees.enums.AppPermission
import com.example.anees.enums.PrayEnum
import com.example.anees.ui.dialog.AneesAlertDialog
import com.example.anees.utils.date_helper.DateHelper
import com.example.anees.utils.extensions.convertNumbersToArabic
import com.example.anees.utils.extensions.getCityAndCountryInArabic
import com.example.anees.utils.extensions.toArabicTime
import com.example.anees.utils.location.LocationProvider
import com.example.anees.utils.prayer_helper.PrayerTimesHelper
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit


@Composable
fun HomeHeader(
    hijriDate: String = "24 رمضان 1445 هـ".convertNumbersToArabic(),
    coordinates: MutableState<Coordinates>,
    prayerName: String = "صلاة الظهر",
    prayerTime: String = "12:45 م".convertNumbersToArabic(),
    remainingTime: String = "5:02:02".convertNumbersToArabic(),
    isSyncing: MutableState<Boolean>,
    onCardClick: () -> Unit = {}
) {
    var showLocationPermissionDialog = remember { mutableStateOf(false) }
    val context = LocalContext.current
    val locationLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->

        if (granted) {
            showLocationPermissionDialog.value = false
            syncLocation(context, coordinates, isSyncing)
        }
    }

    if (showLocationPermissionDialog.value && !AppPermission.Location.isGranted(context)) {
        AneesAlertDialog(
            title = AppPermission.Location.title,
            message = AppPermission.Location.message,
            onConfirmLabel = "سماح",
            onConfirm = { locationLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION) },
            onDismiss = { showLocationPermissionDialog.value = false })
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(top = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                    SyncLocationButton(
                        context = context,
                        showLocationPermissionDialog = showLocationPermissionDialog,
                        coordinates = coordinates,
                        isSyncing = isSyncing
                    )
                Text(
                    text = context.getCityAndCountryInArabic(
                        coordinates.value.latitude, coordinates.value.longitude
                    ), fontSize = 14.sp, fontWeight = FontWeight.Medium, color = Color.Black
                )

            }
            Text(
                text = hijriDate,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(24.dp))
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF803F0B), Color(0xFF5A2E0E), Color(0xFF311403)
                        )
                    )
                )
                .padding(16.dp)
                .clickable(
                    interactionSource = null, indication = null, onClick = onCardClick
                )
        ) {
            Image(
                painter = painterResource(id = R.drawable.l),
                contentDescription = null,
                modifier = Modifier.matchParentSize()
            )
            // العنوان أعلى اليمين
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "الصلاة القادمة",
                        color = Color.White.copy(alpha = 0.8f),
                        fontSize = 14.sp,
                    )
                }


                // التفاصيل الرئيسية في المنتصف
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
/*                    Image(
                        painter = painterResource(id = R.drawable.dhuhr), // استبدل بالأيقونة المناسبة
                        contentDescription = null,
                        modifier = Modifier.size(36.dp)
                    )*/
                    Spacer(modifier = Modifier.height(8.dp))
                    ExtrudedText(
                        text = prayerName,
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = prayerTime,
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .background(
                            Color.White.copy(alpha = 0.15f), shape = RoundedCornerShape(12.dp)
                        )
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                        .align(Alignment.Start)
                ) {
                    Text(
                        text = "الصلاة القادمة خلال :  ${remainingTime}".convertNumbersToArabic(),
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                    )
                }
            }
        }
    }
}

@Composable
fun SyncLocationButton(
    modifier: Modifier = Modifier,
    context: Context,
    showLocationPermissionDialog: MutableState<Boolean>,
    coordinates: MutableState<Coordinates>,
    isSyncing: MutableState<Boolean>
) {
    IconButton(
        onClick = {
            if (!AppPermission.Location.isGranted(context)) {
                showLocationPermissionDialog.value = true
            } else syncLocation(
                coordinates = coordinates,
                context = context,
                isSyncing = isSyncing
            )
        },
        modifier = modifier
    ) {
        Icon(
            imageVector = Icons.Default.Sync,
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = Color(0xFF311403)
        )
    }
}

private fun syncLocation(
    context: Context,
    coordinates: MutableState<Coordinates>,
    isSyncing: MutableState<Boolean>
) {
    isSyncing.value = true
    LocationProvider(context).fetchLatLong { location ->
        coordinates.value = Coordinates(location.latitude, location.longitude)
    }
}

@Composable
fun ExtrudedText(
    text: String = "صلاة الظهر",
    fontSize: TextUnit = 26.sp,
    frontColor: Color = Color.White,
    shadowColor: Color = Color.Black.copy(alpha = 0.6f),
    offsetDp: Dp = 3.dp
) {
    Box {
        // ظل Text
        Text(
            text = text,
            fontSize = fontSize,
            fontWeight = FontWeight.Bold,
            color = shadowColor,
            modifier = Modifier.offset(x = offsetDp, y = offsetDp)
        )
        // النص الأمامي
        Text(
            text = text, fontSize = fontSize, fontWeight = FontWeight.Bold, color = frontColor
        )
    }
}

@Composable
fun PrayerCardWithTimer(
    coordinates: MutableState<Coordinates>,
    isSyncing: MutableState<Boolean>,
    onCardClick: () -> Unit
) {
    val context = LocalContext.current
    var remainingTime by remember { mutableStateOf("") }
//    var location by remember { mutableStateOf("") }

    val (prayEnum, targetTime) = PrayerTimesHelper.getNextPrayer()!!

    /*    LaunchedEffect(Unit) {
            withContext(Dispatchers.IO) {
                var result =""
                if (mylocation == null){
                    val latitude = SharedPreferencesImpl(context).fetchData("latitude", 30.033333)
                    val longitude = SharedPreferencesImpl(context).fetchData("longitude", 31.233334)
                    result = context.getCityAndCountryInArabic(latitude, longitude)
                }
                else{
                    result = context.getCityAndCountryInArabic(mylocation.latitude, mylocation.longitude)
                }

                withContext(Dispatchers.Main) {
                    location = result
                }
            }
        }*/

    LaunchedEffect(targetTime) {
        while (true) {
            val diff = targetTime - System.currentTimeMillis()
            if (diff <= 0) {
                remainingTime = "00:00:00"
                break
            }
            val hours = TimeUnit.MILLISECONDS.toHours(diff)
            val minutes = TimeUnit.MILLISECONDS.toMinutes(diff) % 60
            val seconds = TimeUnit.MILLISECONDS.toSeconds(diff) % 60
            remainingTime = String.format("%02d:%02d:%02d", hours, minutes, seconds)
            delay(1000L)
        }
    }


    HomeHeader(
        hijriDate = DateHelper.getTodayHijriDate(),
        coordinates = coordinates,
        prayerName = if (PrayerTimesHelper.isTodayFriday() && prayEnum == PrayEnum.ZUHR) "صلاة الجمعة" else prayEnum.value,
        prayerTime = targetTime.toArabicTime().convertNumbersToArabic(),
        remainingTime = remainingTime.convertNumbersToArabic(),
        isSyncing = isSyncing
    ) {
        onCardClick()
    }
}


