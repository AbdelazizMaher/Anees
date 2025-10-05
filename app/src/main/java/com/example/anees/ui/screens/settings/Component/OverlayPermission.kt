package com.example.anees.ui.screens.settings.Component

import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

@Composable
fun OverlayPermissionRequester(
    onResult: (Boolean) -> Unit
) {
    val context = LocalContext.current
    val showPermissionDialog = remember { mutableStateOf(false) }

    val requestOverlayPermission = remember {
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!Settings.canDrawOverlays(context)) {
                    // Show dialog to ask for permission
                    showPermissionDialog.value = true
                } else {
                    onResult(true)
                }
            } else {
                onResult(true) // For older versions, automatically grant permission
            }
        }
    }

    if (showPermissionDialog.value) {
        AlertDialog(
            onDismissRequest = { showPermissionDialog.value = false },
            title = { Text("السماح بعرض التنبيهات فوق التطبيقات") },
            text = {
                Text("يرجى السماح للتطبيق بعرض التنبيهات فوق التطبيقات الأخرى من خلال الإعدادات.")
            },
            confirmButton = {
                TextButton(onClick = {
                    showPermissionDialog.value = false
                    val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION).apply {
                        context.startActivity(this)
                    }
                }) {
                    Text("فتح الإعدادات")
                }
            },
            dismissButton = {
                TextButton(onClick = { showPermissionDialog.value = false }) {
                    Text("إلغاء")
                }
            }
        )
    }

    // Call the permission request when needed
    requestOverlayPermission()
}
