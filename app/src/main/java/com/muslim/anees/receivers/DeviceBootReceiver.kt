package com.muslim.anees.receivers

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.muslim.anees.enums.AppPermission
import com.muslim.anees.utils.extensions.scheduleMidnightAlarmReset
import com.muslim.anees.utils.extensions.setAllAlarms

class DeviceBootReceiver : BroadcastReceiver() {

    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    override fun onReceive(context: Context, intent: Intent) {
        if (AppPermission.Alarm.isGranted(context)) {
        context.setAllAlarms()
        }
        if (AppPermission.Notification.isGranted(context)) {
            context.scheduleMidnightAlarmReset()
        }
    }
}