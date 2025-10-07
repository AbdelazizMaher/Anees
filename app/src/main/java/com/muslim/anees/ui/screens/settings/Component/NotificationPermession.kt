package com.muslim.anees.ui.screens.settings.Component

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

@Composable
fun NotificationPermissionRequester(
    onResult: (Boolean) -> Unit,
    onNeverAskAgain: () -> Unit
): () -> Unit {
    val context = LocalContext.current

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (!isGranted && Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val permission = android.Manifest.permission.POST_NOTIFICATIONS
            val showRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(context as Activity, permission)
            if (!showRationale) {
                onNeverAskAgain()
                return@rememberLauncherForActivityResult
            }
        }
        onResult(isGranted)
    }

    return remember {
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                val permission = android.Manifest.permission.POST_NOTIFICATIONS
                val granted = ContextCompat.checkSelfPermission(
                    context,
                    permission
                ) == PackageManager.PERMISSION_GRANTED
                if (!granted) {
                    permissionLauncher.launch(permission)
                } else {
                    onResult(true)
                }
            } else {
                onResult(true)
            }
        }
    }
}


fun canRequestNotificationPermission(context: Context): Boolean {
    val permission = android.Manifest.permission.POST_NOTIFICATIONS
    return ActivityCompat.shouldShowRequestPermissionRationale(context as Activity, permission)
}

