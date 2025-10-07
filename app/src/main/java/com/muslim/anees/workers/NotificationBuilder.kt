package com.muslim.anees.workers

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.muslim.anees.R
import com.muslim.anees.data.local.sharedpreference.SharedPreferencesImpl
import com.muslim.anees.utils.Constants
import com.muslim.anees.utils.sebha_helper.getRandomZekir
import java.util.concurrent.TimeUnit

const val CHANNEL_ID = "my_channel_id"

fun showNotification(context: Context) {
    val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    val largeIcon = BitmapFactory.decodeResource(context.resources, R.drawable.mosque)

    val notificationBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
        .setSmallIcon(R.drawable.mosque)
        .setContentTitle("Anees")
        .setContentText(getRandomZekir().arabicName)
        .setStyle(NotificationCompat.BigTextStyle().bigText(getRandomZekir().arabicName))
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setCategory(NotificationCompat.CATEGORY_RECOMMENDATION)
        .setLargeIcon(largeIcon)
        .setTimeoutAfter(60_000)



    notificationManager.notify(666, notificationBuilder.build())
}


fun createNotificationChannel(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val name = "My Channel"
        val descriptionText = "Anees"
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
            description = descriptionText
        }
        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}

fun setNotification(ctx: Context){
    val interval = SharedPreferencesImpl(ctx).fetchData(Constants.CURRENT_ZEKIR_INTERVAL , 1).toLong()
    val workRequest = PeriodicWorkRequestBuilder<MyPeriodicWorker>(
        interval , TimeUnit.HOURS
    ).build()

    WorkManager.getInstance(ctx).enqueueUniquePeriodicWork(
        "my_periodic_notification",
        ExistingPeriodicWorkPolicy.REPLACE,
        workRequest
    )

}

