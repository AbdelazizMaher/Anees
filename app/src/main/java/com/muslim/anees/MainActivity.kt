package com.muslim.anees

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.batoulapps.adhan.Coordinates
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.muslim.anees.data.local.sharedpreference.SharedPreferencesImpl
import com.muslim.anees.enums.AppPermission
import com.muslim.anees.ui.dialog.PermissionsFlowDialog
import com.muslim.anees.ui.navigation.SetUpNavHost
import com.muslim.anees.utils.SharedModel
import com.muslim.anees.utils.extensions.isLocationEnabled
import com.muslim.anees.utils.extensions.setAllAlarms
import com.muslim.anees.utils.location.LocationProvider
import com.muslim.anees.utils.prayer_helper.PrayerTimesHelper
import dagger.hilt.android.AndroidEntryPoint
import kotlin.system.exitProcess

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController
    private var isSyncing: MutableState<Boolean>? = null
    private var coordinates: MutableState<Coordinates>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        SharedModel.isAppOpen = true
        val locationProvider = LocationProvider(this)
        val context = this

        setContent {
            val syncingState = remember { mutableStateOf(false) }
            val coordinatesState = remember {
                mutableStateOf(PrayerTimesHelper.getCoordinates())
            }
            isSyncing = syncingState
            coordinates = coordinatesState
            navController = rememberNavController()

            val systemUiController = rememberSystemUiController()
            val readyToShowPermissions = remember { mutableStateOf(false) }
            val isFirstTime =
                SharedPreferencesImpl(this).fetchData("is_first_time_permissions", true)

            if (readyToShowPermissions.value && isFirstTime) {
                PermissionsFlowDialog(
                    context = this,
                    onLocationGranted = {
                        locationProvider.fetchLatLong { location ->
                            coordinatesState.value = Coordinates(location.latitude, location.longitude)
                        }
                    },
                    onPermissionsFlowFinished = {
                        SharedPreferencesImpl(this).saveData("is_first_time_permissions", false)
                    }
                )
            }

            LaunchedEffect(coordinatesState.value) {
                SharedPreferencesImpl(context).saveData("latitude", coordinatesState.value.latitude)
                SharedPreferencesImpl(context).saveData("longitude", coordinatesState.value.longitude)
                if (AppPermission.Alarm.isGranted(context)) {
                    setAllAlarms()
                }
                syncingState.value = false
            }

            SideEffect {
                systemUiController.setStatusBarColor(
                    color = Color.Transparent,
                    darkIcons = true
                )
            }

            SetUpNavHost(
                navController = navController,
                readyToShowPermissions = readyToShowPermissions,
                location = coordinatesState,
                isSyncing = syncingState
            )
        }
    }

    override fun onResume() {
        super.onResume()
        SharedModel.isAppActive = true

        if (isSyncing?.value == true) {
            if (this.isLocationEnabled()) {
                LocationProvider(this).fetchLatLong { location ->
                    coordinates?.value = Coordinates(location.latitude, location.longitude)
                    isSyncing?.value = false
                }
            } else {
                isSyncing?.value = false
            }
        }
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