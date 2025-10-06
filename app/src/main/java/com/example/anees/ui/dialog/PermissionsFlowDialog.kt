package com.example.anees.ui.dialog

import android.Manifest
import android.app.AlarmManager
import android.content.Context
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.anees.MainActivity
import com.example.anees.enums.AppPermission
import com.example.anees.utils.extensions.hasOverlayPermission
import com.example.anees.utils.extensions.openAlarmSettings
import com.example.anees.utils.extensions.openOverlaySettings

@Composable
fun PermissionsFlowDialog(
    onLocationGranted: () -> Unit,
    onPermissionsFlowFinished: () -> Unit,
    context: MainActivity
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    val permissionsQueue = remember {
        mutableStateListOf<AppPermission>().apply {
            AppPermission.entries.forEach { permission ->
                if (!permission.isGranted(context)) {
                    add(permission)
                }
            }
        }
    }

    var showDialog by remember { mutableStateOf(true) }

    val notificationLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { _ ->
        permissionsQueue.removeFirstOrNull()
        showDialog = true
    }

    val locationLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        permissionsQueue.removeFirstOrNull()
        showDialog = true
        if (granted) onLocationGranted()
    }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                if (permissionsQueue.isNotEmpty()) {
                    when (permissionsQueue.first()) {
                        AppPermission.Overlay -> {
                            if (context.hasOverlayPermission()) {
                                permissionsQueue.removeFirstOrNull()
                                showDialog = true
                            }
                        }

                        AppPermission.Alarm -> {
                            val granted = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                                val alarmManager =
                                    context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
                                alarmManager.canScheduleExactAlarms()
                            } else true

                            if (granted) {
                                permissionsQueue.removeFirstOrNull()
                                showDialog = true
                            }
                        }

                        else -> {}
                    }
                }
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    }

    val current = permissionsQueue.firstOrNull()
    val (message, title) = when (current) {
        AppPermission.Notification -> AppPermission.Notification.message to AppPermission.Notification.title
        AppPermission.Location -> AppPermission.Location.message to AppPermission.Location.title
        AppPermission.Overlay -> AppPermission.Overlay.message to AppPermission.Overlay.title
        AppPermission.Alarm ->  AppPermission.Alarm.message to AppPermission.Alarm.title
        else -> null to null
    }

    fun skipPermission() {
        permissionsQueue.removeFirstOrNull()
        showDialog = true
    }

    if (message != null && showDialog) {
        AneesAlertDialog(
            title = title ?: "",
            message = message,
            onConfirmLabel = "سماح",
            onConfirm = {
                showDialog = false
                when (current) {
                    AppPermission.Notification -> {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            notificationLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                        } else skipPermission()
                    }

                    AppPermission.Location -> locationLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                    AppPermission.Overlay -> {
                        if (context.hasOverlayPermission()) skipPermission()
                        else context.openOverlaySettings()
                    }

                    AppPermission.Alarm -> context.openAlarmSettings {
                        skipPermission()
                    }

                    else -> {}
                }
            },
            onDismiss = {
                skipPermission()
            }
        )
    } else if (permissionsQueue.isEmpty()) {
        onPermissionsFlowFinished()
    }
}





