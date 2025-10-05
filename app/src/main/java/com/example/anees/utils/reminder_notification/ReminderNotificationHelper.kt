package com.example.anees.utils.reminder_notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.net.toUri
import com.example.anees.R

fun getReminderNotificationSound(azan:Int,context: Context) : Uri {
    return when (azan) {
        6 -> "android.resource://${context.packageName}/${R.raw.fajr}".toUri()
        7 -> "android.resource://${context.packageName}/${R.raw.duhr}".toUri()
        8 -> "android.resource://${context.packageName}/${R.raw.asr}".toUri()
        9 -> "android.resource://${context.packageName}/${R.raw.maghreb}".toUri()
        10 -> "android.resource://${context.packageName}/${R.raw.isha}".toUri()
        else -> RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
    }
}

fun getReminderNotificationIcon(azan: Int): Int{
    return when (azan) {
        6 -> R.drawable.fajr
        7 -> R.drawable.dhuhr
        8 -> R.drawable.asr
        9 -> R.drawable.maghrib
        10 -> R.drawable.isha
        else -> R.drawable.maghrib
    }
}

fun createReminderNotification(soundType: Int, context: Context){
    val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    val channelId = "azan_reminder_notification$soundType"
    val azan = when (soundType) {
        6 -> "الفجر"
        7 -> "الظهر"
        8 -> "العصر"
        9 -> "المغرب"
        10 -> "العشاء"
        else -> "Unknown"
    }
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val soundUri = getReminderNotificationSound(soundType,context)

        val channel = NotificationChannel(
            channelId,
            azan,
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            setSound(soundUri, null) // set custom sound here
        }

        notificationManager.createNotificationChannel(channel)
    }

    val notificationIcon = getReminderNotificationIcon(soundType)

    val notification = NotificationCompat.Builder(context, channelId)
        .setContentTitle("اقتربت صلاة $azan")
        .setContentText("إِنَّ الصَّلَاةَ كَانَتْ عَلَى الْمُؤْمِنِينَ كِتَابًا مَّوْقُوتًا")
        .setSmallIcon(notificationIcon)
        .build()

    notificationManager.notify(soundType, notification)
}

fun mapPrayEnumToNumber(prayEnum: String): Int{
    return when (prayEnum) {
        "صلاة الفجر" -> 6
        "صلاة الظهر" -> 7
        "صلاة العصر" -> 8
        "صلاة المغرب" -> 9
        "صلاة العشاء" -> 10
        else -> 0
    }
}