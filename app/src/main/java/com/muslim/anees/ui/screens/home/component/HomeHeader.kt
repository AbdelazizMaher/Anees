package com.muslim.anees.ui.screens.home.component

import android.Manifest
import android.content.Context
import android.content.Intent
import android.provider.Settings
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
import com.muslim.anees.R
import com.muslim.anees.enums.AppPermission
import com.muslim.anees.enums.PrayEnum
import com.muslim.anees.ui.dialog.rememberPermissionRequestHandler
import com.muslim.anees.utils.date_helper.DateHelper
import com.muslim.anees.utils.extensions.convertNumbersToArabic
import com.muslim.anees.utils.extensions.getCityAndCountryInArabic
import com.muslim.anees.utils.extensions.isLocationEnabled
import com.muslim.anees.utils.extensions.toArabicTime
import com.muslim.anees.utils.location.LocationProvider
import com.muslim.anees.utils.prayer_helper.PrayerTimesHelper
import com.muslim.anees.widgets.WidgetUpdater
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
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
    val context = LocalContext.current
    val locationPermissionHandler = rememberPermissionRequestHandler(
        permission = Manifest.permission.ACCESS_FINE_LOCATION,
        title = AppPermission.Location.title,
        message = AppPermission.Location.message,
        rationaleTitle = "إذن الموقع مطلوب",
        rationaleMessage = "لا يمكننا تحديد موقعك بدقة بدون إذن الوصول إلى الموقع.\n"
                + "هذا الإذن ضروري لحساب مواقيت الصلاة الصحيحة في منطقتك.\n"
                + "يرجى تفعيل إذن الموقع من إعدادات التطبيق يدويًا.",
        onGranted = { syncLocation(context, coordinates, isSyncing) },
        permissionToBeChecked = AppPermission.Location
    )

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
                SyncLocationButton(onSync = { locationPermissionHandler() })
                Text(
                    text = context.getCityAndCountryInArabic(), fontSize = 14.sp, fontWeight = FontWeight.Medium, color = Color.Black
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


                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
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
fun SyncLocationButton(onSync: () -> Unit) {
    IconButton(
        onClick = { onSync() }
    ) {
        Icon(
            imageVector = Icons.Default.Sync,
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = Color(0xFF311403)
        )
    }
}

fun syncLocation(
    context: Context,
    coordinates: MutableState<Coordinates>,
    isSyncing: MutableState<Boolean>
) {
    isSyncing.value = true

    if (!context.isLocationEnabled()) {
        val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
        context.startActivity(intent)
        return
    }
    LocationProvider(context).fetchLatLong { location ->
        coordinates.value = Coordinates(location.latitude, location.longitude)
        isSyncing.value = false
    }

    CoroutineScope(Dispatchers.IO).launch {
        WidgetUpdater.refreshWidgets(context)
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
    var remainingTime by remember { mutableStateOf("") }
    val (prayEnum, targetTime) = PrayerTimesHelper.getNextPrayer()!!
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


