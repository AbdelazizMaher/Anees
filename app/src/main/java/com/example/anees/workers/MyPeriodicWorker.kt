package com.example.anees.workers

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.anees.data.local.sharedpreference.SharedPreferencesImpl
import com.example.anees.utils.Constants

class MyPeriodicWorker(context: Context, params: WorkerParameters) : Worker(context, params) {
    override fun doWork(): Result {

        val permission =  SharedPreferencesImpl(applicationContext).fetchData(Constants.NOTIFICATION_STATE , true)
        Log.d("TAG", "doWork: $permission")
        if (!permission) return Result.success()

        createNotificationChannel(applicationContext)
        showNotification(applicationContext)
        return Result.success()
    }
}
