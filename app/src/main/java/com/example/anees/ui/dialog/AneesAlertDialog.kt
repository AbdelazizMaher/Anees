package com.example.anees.ui.dialog

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextAlign

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
        text = { Text(message, textAlign = TextAlign.Center) },
        confirmButton = {
            TextButton(onClick = onConfirm) { Text(onConfirmLabel) }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("تخطي") }
        }
    )
}