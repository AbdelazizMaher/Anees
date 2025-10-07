package com.muslim.anees

import android.app.Application
import com.muslim.anees.utils.extensions.scheduleMidnightAlarmReset
import com.muslim.anees.utils.prayer_helper.PrayerTimesHelper
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class App : Application(){

    override fun onCreate() {
        super.onCreate()
        scheduleMidnightAlarmReset()
        PrayerTimesHelper.init(this)
    }
}