package com.example.anees.ui.dialog

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection

@Composable
fun AneesAlertDialog(
    message: String,
    onConfirmLabel: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    title: String
) {
    AlertDialog(
        onDismissRequest = {},
        title = { Text(title) },
        text = {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                Text(message, textAlign = TextAlign.Start)
            }
        },
        confirmButton = {
            TextButton(onClick = onConfirm) { Text(onConfirmLabel) }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("تخطي") }
        }
    )
}