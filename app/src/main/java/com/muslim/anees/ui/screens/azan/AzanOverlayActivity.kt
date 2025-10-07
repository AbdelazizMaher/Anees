package com.muslim.anees.ui.screens.azan

import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import com.muslim.anees.enums.PrayEnum
import com.muslim.anees.utils.SharedModel
import com.muslim.anees.utils.extensions.convertNumbersToArabic
import com.muslim.anees.utils.extensions.setAllAlarms
import com.muslim.anees.utils.extensions.toArabicTime
import dagger.hilt.android.AndroidEntryPoint
import kotlin.system.exitProcess


@AndroidEntryPoint
class AzanOverlayActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setAllAlarms()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            setShowWhenLocked(true)
            setTurnScreenOn(true)
        } else {
            window.addFlags(
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or
                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON or
                        WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
            )
        }


        val prayEnum = intent.getSerializableExtra("prayEnum") as? PrayEnum
        val time = intent.getLongExtra("time" , 0)

        enableEdgeToEdge()
        setContent {
            AzanScreen(
                prayEnum = prayEnum ?: PrayEnum.FAJR,
                prayerTime = time.toArabicTime().convertNumbersToArabic(),
            ){
                setAllAlarms()
                finish()
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        if (!SharedModel.isAppOpen) {
            finish()
            val activityManager = getSystemService(ACTIVITY_SERVICE) as android.app.ActivityManager
            val appTasks = activityManager.appTasks
            for (task in appTasks) {
                task.finishAndRemoveTask()
            }
            android.os.Process.killProcess(android.os.Process.myPid())
            exitProcess(0)
        }
    }









}