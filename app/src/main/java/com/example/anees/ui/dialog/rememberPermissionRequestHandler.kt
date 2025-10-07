package com.example.anees.ui.dialog

import androidx.activity.compose.LocalActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.example.anees.enums.AppPermission
import com.example.anees.utils.extensions.isPermissionPermanentlyDenied
import com.example.anees.utils.extensions.openAppSettings

@Composable
fun rememberPermissionRequestHandler(
    permission: String,
    title: String,
    message: String,
    permissionToBeChecked: AppPermission,
    rationaleTitle: String,
    rationaleMessage: String,
    onDeclined: () -> Unit = {},
    onGranted: () -> Unit
): () -> Unit {
    val context = LocalContext.current
    val activity = LocalActivity.current

    val showRationaleDialog = remember { mutableStateOf(false) }
    val showPermissionDialog = remember { mutableStateOf(false) }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            showPermissionDialog.value = false
            onGranted()
        } else {
            if (activity != null && activity.isPermissionPermanentlyDenied(permission)) {
                showRationaleDialog.value = true
            } else {
                showPermissionDialog.value = true
            }
            onDeclined()
        }
    }

    // -------- Permission Dialog --------
    if (showPermissionDialog.value && !permissionToBeChecked.isGranted(context)) {
        AneesAlertDialog(
            title = title,
            message = message,
            onConfirmLabel = "سماح",
            onConfirm = {
                permissionLauncher.launch(permission)
                showPermissionDialog.value = false
            },
            onDismiss = { showPermissionDialog.value = false }
        )
    }

    // -------- Rationale Dialog --------
    if (showRationaleDialog.value) {
        AneesAlertDialog(
            title = rationaleTitle,
            message = rationaleMessage,
            onConfirmLabel = "فتح الإعدادات",
            onConfirm = {
                openAppSettings(context)
                showRationaleDialog.value = false
            },
            onDismiss = { showRationaleDialog.value = false }
        )
    }

    return {
        if (!permissionToBeChecked.isGranted(context)) {
            showPermissionDialog.value = true
        } else {
            onGranted()
        }
    }
}