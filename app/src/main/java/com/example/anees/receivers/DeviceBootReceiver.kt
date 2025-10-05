package com.example.anees.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.anees.MainActivity
import com.example.anees.enums.AppPermission
import com.example.anees.utils.extensions.scheduleMidnightAlarmReset
import com.example.anees.utils.extensions.setAllAlarms

class DeviceBootReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (AppPermission.Alarm.isGranted(context)) {
        context.setAllAlarms()
        }
        if (AppPermission.Notification.isGranted(context)) {
            context.scheduleMidnightAlarmReset()
        }
    }
}