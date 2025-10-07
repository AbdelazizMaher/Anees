package com.example.anees.ui.screens.prayer.component

import android.Manifest
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.batoulapps.adhan.Coordinates
import com.example.anees.enums.AppPermission
import com.example.anees.ui.dialog.rememberPermissionRequestHandler
import com.example.anees.ui.screens.home.component.SyncLocationButton
import com.example.anees.ui.screens.home.component.syncLocation
import com.example.anees.utils.extensions.getCityAndCountryInArabic

@Composable
fun PrayerTopBar(
    coordinates: MutableState<Coordinates>,
    onBackClick: () -> Unit = {},
    isSyncing: MutableState<Boolean>
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

    Row(
        Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        LocationChip(
            location = context.getCityAndCountryInArabic(
                coordinates.value.latitude, coordinates.value.longitude
            ), icon = Icons.Default.LocationOn
        )
        SyncLocationButton{ locationPermissionHandler() }
        Spacer(modifier = Modifier.weight(1f))
        IconButton(
            onClick = onBackClick,
            modifier = Modifier.size(48.dp),
        ) {
            Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Back")
        }
    }
}

@Composable
fun LocationChip(
    location: String,
    icon: ImageVector,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .background(Color(0xFFE0E0E0), shape = RoundedCornerShape(50.dp))
            .padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color(0xFFCD0202),
            modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = location,
            fontSize = 14.sp,
            color = Color.Black
        )
    }
}
