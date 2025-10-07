package com.muslim.anees.ui.screens.prayer.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.muslim.anees.enums.PrayEnum
import com.muslim.anees.utils.extensions.convertNumbersToArabic
import com.muslim.anees.utils.extensions.toArabicTime
import com.muslim.anees.utils.prayer_helper.PrayerTimesHelper

@Composable
fun PrayerList() {
    val prayers = PrayerTimesHelper.getAllPrayers()

    Column {
        prayers.forEach { (prayerEnum, time , isHighlighted) ->
            val name = if (PrayerTimesHelper.isTodayFriday() && prayerEnum == PrayEnum.ZUHR ) "صلاة الجمعة" else prayerEnum.value
            val icon = prayerEnum.icon
            PrayerItem(
                name = name,
                time = time.toArabicTime().convertNumbersToArabic(),
                icon = icon,
                isHighlighted = isHighlighted
            )
        }
    }
}

@Composable
fun PrayerItem(
    name: String,
    time: String,
    icon: Int ,
    isHighlighted: Boolean
) {
    Row(
        modifier = Modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth()
            .background(
                if (isHighlighted) Color(0xFFDFF5E3) else Color.White,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(horizontal = 16.dp, vertical = 12.dp)
        ,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = icon),
                contentDescription = null,
                modifier = Modifier.size(36.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = name,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )
        }
        Text(
            text = time,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = Color.Black
        )
    }
}

