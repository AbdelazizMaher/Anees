package com.example.anees

import android.app.Application
import com.example.anees.utils.extensions.scheduleMidnightAlarmReset
import com.example.anees.utils.prayer_helper.PrayerTimesHelper
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class App : Application(){

    override fun onCreate() {
        super.onCreate()
        scheduleMidnightAlarmReset()
        PrayerTimesHelper.init(this)
    }
}