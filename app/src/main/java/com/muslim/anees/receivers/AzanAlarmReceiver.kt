package com.muslim.anees.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.muslim.anees.data.local.sharedpreference.SharedPreferencesImpl
import com.muslim.anees.enums.PrayEnum
import com.muslim.anees.ui.screens.azan.AzanOverlayActivity
import com.muslim.anees.utils.Constants
import com.muslim.anees.utils.reminder_notification.createReminderNotification

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
