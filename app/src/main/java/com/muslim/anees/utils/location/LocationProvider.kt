package com.muslim.anees.utils.location

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.os.Looper
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority

class LocationProvider(private val context: Context) {
    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    private lateinit var locationCallback: LocationCallback

    fun fetchLatLong(onResult: (Location) -> Unit) {
        if (!context.isLocationEnabled()) {
            context.enableLocationService()
            return
        }

        getFreshLocation { location ->
            onResult(location)
        }
    }

    @SuppressLint("MissingPermission")
    private fun getFreshLocation(onLocationFetched: (Location) -> Unit) {
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                val location = locationResult.lastLocation
                location?.let { onLocationFetched(it) }
                fusedLocationClient.removeLocationUpdates(this)
            }
        }

        fusedLocationClient.requestLocationUpdates(
            LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 1000)
                .setWaitForAccurateLocation(true)
                .build(),
            locationCallback,
            Looper.getMainLooper()
        )
    }

}