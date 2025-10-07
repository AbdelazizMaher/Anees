package com.muslim.anees.workers

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.muslim.anees.data.local.sharedpreference.SharedPreferencesImpl
import com.muslim.anees.utils.Constants

class MyPeriodicWorker(context: Context, params: WorkerParameters) : Worker(context, params) {
    override fun doWork(): Result {

        val permission =  SharedPreferencesImpl(applicationContext).fetchData(Constants.NOTIFICATION_STATE , true)
        if (!permission) return Result.success()

        createNotificationChannel(applicationContext)
        showNotification(applicationContext)
        return Result.success()
    }
}
