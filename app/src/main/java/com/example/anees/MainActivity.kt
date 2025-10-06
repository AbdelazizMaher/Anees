package com.example.anees


import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.batoulapps.adhan.Coordinates
import com.example.anees.data.local.sharedpreference.SharedPreferencesImpl
import com.example.anees.enums.AppPermission
import com.example.anees.ui.dialog.PermissionsFlowDialog
import com.example.anees.ui.navigation.SetUpNavHost
import com.example.anees.utils.SharedModel
import com.example.anees.utils.extensions.setAllAlarms
import com.example.anees.utils.location.LocationProvider
import com.example.anees.utils.prayer_helper.PrayerTimesHelper
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import kotlin.system.exitProcess

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    lateinit var navController: NavHostController
    lateinit var locationProvider: LocationProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        SharedModel.isAppOpen = true
        val locationProvider = LocationProvider(this)
        val context = this

        setContent {
            val isSyncing = remember { mutableStateOf(false) }
            navController = rememberNavController()
            val coordinates = remember { mutableStateOf(PrayerTimesHelper.getCoordinates()) }
            val systemUiController = rememberSystemUiController()
            val readyToShowPermissions = remember { mutableStateOf(false) }
            val isFirstTime =
                SharedPreferencesImpl(this).fetchData("is_first_time_permissions", true)
            if (readyToShowPermissions.value && isFirstTime) {
                PermissionsFlowDialog(context = this, onLocationGranted = {
                    locationProvider.fetchLatLong() { location ->
                        coordinates.value = Coordinates(location.latitude, location.longitude)
                    }
                }, onPermissionsFlowFinished = {
                    SharedPreferencesImpl(this).saveData("is_first_time_permissions", false)
                })
            }
            LaunchedEffect(coordinates.value) {
                SharedPreferencesImpl(context).saveData("latitude", coordinates.value.latitude)
                SharedPreferencesImpl(context).saveData("longitude", coordinates.value.longitude)
                if (AppPermission.Alarm.isGranted(context)) {
                    setAllAlarms()
                }
                isSyncing.value = false
            }
            SideEffect {
                systemUiController.setStatusBarColor(
                    color = Color.Transparent, darkIcons = true
                )
            }

            SetUpNavHost(
                navController = navController, readyToShowPermissions = readyToShowPermissions,
                location = coordinates,
                isSyncing = isSyncing
            )
        }

    }

    override fun onResume() {
        super.onResume()
        SharedModel.isAppActive = true
    }

    override fun onPause() {
        super.onPause()
        SharedModel.isAppActive = false
    }

    override fun onDestroy() {
        super.onDestroy()
        if (!SharedModel.isAppActive) {
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







