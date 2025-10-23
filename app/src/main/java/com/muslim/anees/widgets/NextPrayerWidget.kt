package com.muslim.anees.widgets

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.provideContent
import androidx.glance.appwidget.updateAll
import androidx.glance.layout.*
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import androidx.glance.action.actionStartActivity
import androidx.glance.action.clickable
import com.muslim.anees.MainActivity
import com.muslim.anees.enums.PrayEnum
import com.muslim.anees.utils.extensions.convertNumbersToArabic
import com.muslim.anees.utils.extensions.toArabicTime
import com.muslim.anees.utils.prayer_helper.PrayerTimesHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object NextPrayerWidget : GlanceAppWidget() {

    @SuppressLint("RestrictedApi")
    @Composable
    fun Content() {
        val (prayEnum, targetTime) = PrayerTimesHelper.getNextPrayer()!!

        val  prayerName = if (PrayerTimesHelper.isTodayFriday() && prayEnum == PrayEnum.ZUHR) "صلاة الجمعة" else prayEnum.value
        val prayerTime = targetTime.toArabicTime().convertNumbersToArabic()
        // Define a click action to open the app
        val openAppAction = actionStartActivity<MainActivity>()

        Box(
            modifier = GlanceModifier
                .fillMaxSize()
                .padding(12.dp)
                .clickable(onClick = openAppAction)
        ) {
            Column(
                modifier = GlanceModifier.fillMaxWidth()
            ) {
                // Header (Arabic)
                Text(
                    text = "الصلاة القادمة",
                    style = TextStyle(color = ColorProvider(Color.White), fontWeight = FontWeight.Medium)
                )

                Spacer(modifier = GlanceModifier.height(8.dp))

                // Prayer name
                Text(
                    text = prayerName,
                    style = TextStyle(color = ColorProvider(Color.White), fontWeight = FontWeight.Bold)
                )

                Spacer(modifier = GlanceModifier.height(4.dp))

                // Prayer time
                Text(
                    text = prayerTime,
                    style = TextStyle(color = ColorProvider(Color.White), fontWeight = FontWeight.Medium)
                )

                Spacer(modifier = GlanceModifier.height(8.dp))

                // Small footer text (placeholder for remaining time)
                Text(
                    text = "الصلاة القادمة خلال: —",
                    style = TextStyle(color = ColorProvider(Color.White))
                )
            }
        }
    }

    // Provide the required implementation so Glance can render this widget.
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            Content()
        }
    }
}

class NextPrayerWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget = NextPrayerWidget
}

object WidgetDataHolder {
    var nextPrayerName: String = "Fajr"
    var nextPrayerTime: String = "5:01 AM"

    @Suppress("unused") // may be invoked from other codepaths; keep available
    fun update(nextName: String, nextTime: String, context: Context? = null) {
        nextPrayerName = nextName
        nextPrayerTime = nextTime
        if (context != null) {
            CoroutineScope(Dispatchers.IO).launch {
                // request Glance to refresh all instances of this widget
                NextPrayerWidget.updateAll(context)
            }
        }
    }
}
