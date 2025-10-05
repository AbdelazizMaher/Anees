package com.example.anees.receivers

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.net.toUri
import com.example.anees.R
import com.example.anees.data.local.sharedpreference.SharedPreferencesImpl
import com.example.anees.enums.PrayEnum
import com.example.anees.ui.screens.azan.AzanOverlayActivity
import com.example.anees.utils.Constants
import com.example.anees.utils.prayer_helper.PrayerTimesHelper
import com.example.anees.utils.reminder_notification.createReminderNotification
import com.example.anees.utils.reminder_notification.getReminderNotificationIcon
import com.example.anees.utils.reminder_notification.getReminderNotificationSound

class AzanAlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {

        if (intent?.hasExtra("soundType") == true){
            val soundType = intent.getIntExtra("soundType", 0)
            createReminderNotification(soundType, context)
        }else {

            val prayEnum = intent?.getSerializableExtra("prayEnum") as? PrayEnum
            val time = intent?.getLongExtra("time", 0)
            val overlayIntent = Intent(context, AzanOverlayActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
                putExtra("prayEnum", prayEnum)
                putExtra("time", time)
            }

            val state =
                SharedPreferencesImpl(context).fetchData(Constants.AZAN_NOTIFICATION_STATE, true)
            Log.e("TAG", "onReceive000000000000: $state",)
            if (state) {
                context.startActivity(overlayIntent)
            }

        }
    }
}
