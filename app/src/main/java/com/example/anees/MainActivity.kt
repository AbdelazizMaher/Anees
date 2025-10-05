package com.example.anees


import android.os.Bundle
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

    //    private var askedForOverlayPermission = false // TODO
    lateinit var locationProvider: LocationProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        SharedModel.isAppOpen = true
        val locationProvider = LocationProvider(this)
        val context = this

        setContent {
            navController = rememberNavController()
            var coordinates = remember { mutableStateOf(PrayerTimesHelper.getCoordinates()) }
            val systemUiController = rememberSystemUiController()
            val readyToShowPermissions = remember { mutableStateOf(false) }
            val isFirstTime =
                SharedPreferencesImpl(this).fetchData("is_first_time_permissions", true)
            if (readyToShowPermissions.value && isFirstTime) {
                PermissionsFlowDialog(
                    context = this,
                    onLocationGranted = {
                        locationProvider.fetchLatLong() { location ->
                            coordinates.value = Coordinates(location.latitude, location.longitude)
                        }
                    },
                    onPermissionsFlowFinished = {
                        SharedPreferencesImpl(this).saveData("is_first_time_permissions", false)
                    }
                )
            }
            LaunchedEffect(coordinates) {
                SharedPreferencesImpl(context).saveData("latitude", coordinates.value.latitude)
                SharedPreferencesImpl(context).saveData("longitude", coordinates.value.longitude)
                if (AppPermission.Alarm.isGranted(context)) {
                    setAllAlarms()
                }
            }
            SideEffect {
                systemUiController.setStatusBarColor(
                    color = Color.Transparent,
                    darkIcons = true
                )
            }
//            if (checkPermission() && location == null) {
//                if (location == null) {
//
//                    locationProvider.fetchLatLong(this) { loc ->
//                        location = Coordinates(loc.latitude, loc.longitude)
//                        SharedPreferencesImpl(this).saveData("latitude", loc.latitude)
//                        SharedPreferencesImpl(this).saveData("longitude", loc.longitude)
//                    }
//                }
//            }
            SetUpNavHost(
                navController = navController,
                readyToShowPermissions = readyToShowPermissions,
//                location = getCityAndCountryInArabic(coordinates.latitude, coordinates.longitude)
                location = coordinates
            )
        }

    }

    override fun onResume() {
        super.onResume()
        SharedModel.isAppActive = true
//        if (askedForOverlayPermission && Settings.canDrawOverlays(this)) {
//            askedForOverlayPermission = false
//            SharedPreferencesImpl(this).saveData(Constants.AZAN_NOTIFICATION_STATE, true)
//             locationProvider = LocationProvider(this)
//            locationProvider.fetchLatLong(this) { location ->
//                SharedPreferencesImpl(this).saveData("latitude", location.latitude)
//                SharedPreferencesImpl(this).saveData("longitude", location.longitude)
//                setAllAlarms()
//            }
//        }

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

    /*   override fun onRequestPermissionsResult(
           requestCode: Int,
           permissions: Array<String>,
           grantResults: IntArray
       ) {
           super.onRequestPermissionsResult(requestCode, permissions, grantResults)

           if (requestCode == REQUEST_LOCATION_CODE) {
               val prefs = getSharedPreferences("permission_prefs", MODE_PRIVATE)

               if (grantResults.isNotEmpty() && grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                   Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
                   prefs.edit() { putInt("location_denial_count", 0)}
                   locationProvider.fetchLatLong(this@MainActivity) { location ->
                       SharedPreferencesImpl(this@MainActivity).saveData("latitude", location.latitude)
                       SharedPreferencesImpl(this@MainActivity).saveData("longitude", location.longitude)
                       setAllAlarms()
                   }
               } else {
                   val currentCount = prefs.getInt("location_denial_count", 0)
                   prefs.edit() { putInt("location_denial_count", currentCount + 1) }
                   Toast.makeText(this, "Permission Denied (${currentCount + 1})", Toast.LENGTH_SHORT)
                       .show()
               }
           }
       }*/

}







