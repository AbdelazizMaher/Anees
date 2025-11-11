package com.muslim.anees.widgets

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.LocalContext
import androidx.glance.action.actionStartActivity
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.height
import androidx.glance.layout.padding
import androidx.glance.layout.size
import androidx.glance.layout.width
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import com.muslim.anees.MainActivity
import com.muslim.anees.R
import com.muslim.anees.enums.PrayEnum
import com.muslim.anees.utils.date_helper.DateHelper
import com.muslim.anees.utils.extensions.convertNumbersToArabic
import com.muslim.anees.utils.extensions.getCityAndCountryInArabic
import com.muslim.anees.utils.extensions.toArabicTime
import com.muslim.anees.utils.prayer_helper.PrayerTimesHelper

object NextPrayerWidget : GlanceAppWidget() {

    @SuppressLint("RestrictedApi")
    @Composable
    fun Content() {
        val (prayEnum, targetTime) = PrayerTimesHelper.getNextPrayer()!!
        val prayerName =
            if (PrayerTimesHelper.isTodayFriday() && prayEnum == PrayEnum.ZUHR) "صلاة الجمعة"
            else prayEnum.value
        val prayerTime = targetTime.toArabicTime().convertNumbersToArabic()
        val openAppAction = actionStartActivity<MainActivity>()
        val context = LocalContext.current

        Box(
            modifier = GlanceModifier
                .fillMaxSize()
                .background(ImageProvider(R.drawable.widget_brown_bg))
                .clickable(onClick = openAppAction)
                .padding(8.dp)
        ) {
            // Background mosque overlay
            Image(
                provider = ImageProvider(R.drawable.l),
                contentDescription = null,
                modifier = GlanceModifier
                    .fillMaxSize()
                    .background(Color.Transparent)
            )

            Column(
                modifier = GlanceModifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Top Section
                Row(
                    modifier = GlanceModifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Left side — prayer info
                    Column(
                        modifier = GlanceModifier.defaultWeight(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                provider = ImageProvider(prayEnum.icon),
                                contentDescription = null,
                                modifier = GlanceModifier.size(28 .dp)
                            )
                            Spacer(modifier = GlanceModifier.width(6 .dp))
                            Text(
                                text = prayerName,
                                style = TextStyle(
                                    color = ColorProvider(Color.White),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 22.sp
                                )
                            )
                        }

                        Spacer(modifier = GlanceModifier.height(4 .dp))
                        Text(
                            text = prayerTime,
                            style = TextStyle(
                                color = ColorProvider(Color.White),
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )
                        )
                    }

                    // Right side — location/date
                    Column(
                        modifier = GlanceModifier.defaultWeight(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = context.getCityAndCountryInArabic(),
                            style = TextStyle(
                                color = ColorProvider(Color.White.copy(alpha = 0.9f)),
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Spacer(modifier = GlanceModifier.height(2.dp))
                        Text(
                            text = DateHelper.getTodayHijriDate(),
                            style = TextStyle(
                                color = ColorProvider(Color.White.copy(alpha = 0.9f)),
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Medium
                            )
                        )
                    }
                }

                // Bottom Section — All prayers
                AllPrayersRow()
            }
        }
    }

    @SuppressLint("RestrictedApi")
    @Composable
    private fun AllPrayersRow() {
        val prayers = PrayerTimesHelper.getAllPrayers().reversed()

        Row(
            modifier = GlanceModifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalAlignment = Alignment.CenterVertically
        ) {
            prayers.forEachIndexed { index, (prayerEnum, time, isHighlighted) ->
                val prayerName =
                    if (PrayerTimesHelper.isTodayFriday() && prayerEnum == PrayEnum.ZUHR)
                        "الجمعة" else prayerEnum.value.replace("صلاة ", "")
                val prayerTime = time.toArabicTime().convertNumbersToArabic()

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = GlanceModifier
                        .then(
                            if (isHighlighted) {
                                GlanceModifier
                                    .background(ImageProvider(R.drawable.rounded_brown_box))
                                    .padding(horizontal = 8 .dp, vertical =6 .dp)
                            } else {
                                GlanceModifier.padding(horizontal = 4.dp, vertical = 4.dp)
                            }
                        )
                ) {
                    Text(
                        text = prayerName,
                        style = TextStyle(
                            color = ColorProvider(
                                if (isHighlighted) Color.White else Color.White.copy(alpha = 0.8f)
                            ),
                            fontSize = 16.sp,
                            fontWeight = if (isHighlighted) FontWeight.Bold else FontWeight.Medium
                        ),
                        maxLines = 1
                    )
                    Text(
                        text = prayerTime,
                        style = TextStyle(
                            color = ColorProvider(
                                if (isHighlighted) Color.White else Color.White.copy(alpha = 0.7f)
                            ),
                            fontSize = 14.sp
                        )
                    )
                }

                if (index < prayers.size - 1) {
                    Spacer(modifier = GlanceModifier.width(2.dp))
                }
            }
        }
    }

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent { Content() }
    }
}

class NextPrayerWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget = NextPrayerWidget
}
