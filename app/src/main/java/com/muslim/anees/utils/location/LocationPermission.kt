package com.muslim.anees.utils.location


import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.provider.Settings
import androidx.core.content.ContextCompat

fun Context.enableLocationService() {
    val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
    this.startActivity(intent)
}

fun Context.isLocationEnabled(): Boolean {
    val locationManager = ContextCompat.getSystemService(this, LocationManager::class.java)
    return locationManager?.isProviderEnabled(LocationManager.GPS_PROVIDER) == true ||
            locationManager?.isProviderEnabled(LocationManager.NETWORK_PROVIDER) == true
}
