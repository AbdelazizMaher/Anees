package com.example.anees.ui.screens.qibla

import android.app.Application
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin

@HiltViewModel
class QiblaViewModel @Inject constructor(application: Application) : AndroidViewModel(application), SensorEventListener {

    private val sensorManager = application.getSystemService(Context.SENSOR_SERVICE) as SensorManager

    private val _deviceAzimuth = mutableStateOf(0f)
    val deviceAzimuth = _deviceAzimuth

    private val _bearingToQibla = mutableStateOf(0f)
    val bearingToQibla = _bearingToQibla

    private val rotationMatrix = FloatArray(9)
    private val orientationAngles = FloatArray(3)

    private var qiblaDirection = 0f
    private var lastAzimuth = 0f
    private val alpha = 0.1f

    init {
        registerSensor()
    }

    private fun registerSensor() {
        sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)?.also { rotationVectorSensor ->
            sensorManager.registerListener(this, rotationVectorSensor, SensorManager.SENSOR_DELAY_UI)
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_ROTATION_VECTOR) {
            SensorManager.getRotationMatrixFromVector(rotationMatrix, event.values)
            SensorManager.getOrientation(rotationMatrix, orientationAngles)

            val azimuthRad = orientationAngles[0]
            val azimuthDeg = Math.toDegrees(azimuthRad.toDouble()).toFloat()
            val deviceHeading = (azimuthDeg + 360) % 360

            _deviceAzimuth.value = deviceHeading

            val smoothedBearing = calculateSmoothedBearing(deviceHeading)

            lastAzimuth = smoothedBearing

            if (kotlin.math.abs(_bearingToQibla.value - smoothedBearing) > 1f) {
                _bearingToQibla.value = smoothedBearing
                lastAzimuth = smoothedBearing
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    override fun onCleared() {
        super.onCleared()
        sensorManager.unregisterListener(this)
    }

    fun updateQiblaDirection(lat: Double, lon: Double) {
        qiblaDirection = calculateQiblaAngle(lat, lon)
    }

    private fun calculateQiblaAngle(lat: Double, lon: Double): Float {
        val kaabaLat = Math.toRadians(21.4225)
        val kaabaLon = Math.toRadians(39.8262)

        val userLat = Math.toRadians(lat)
        val userLon = Math.toRadians(lon)

        val deltaLon = kaabaLon - userLon
        val y = sin(deltaLon) * cos(kaabaLat)
        val x = cos(userLat) * sin(kaabaLat) - sin(userLat) * cos(kaabaLat) * cos(deltaLon)
        val angle = Math.toDegrees(atan2(y, x))

        return ((angle + 360) % 360).toFloat()
    }

    private fun calculateSmoothedBearing(deviceAzimuth: Float): Float {
        val bearingToQibla = (qiblaDirection - deviceAzimuth + 360) % 360
        val diff = bearingToQibla - lastAzimuth

        val smoothedBearing = when {
            diff > 180 -> lastAzimuth + (diff - 360)
            diff < -180 -> lastAzimuth + (diff + 360)
            else -> lastAzimuth + diff
        }

        val finalSmoothedBearing = lastAzimuth + alpha * (smoothedBearing - lastAzimuth)

        lastAzimuth = finalSmoothedBearing
        return finalSmoothedBearing
    }

}

