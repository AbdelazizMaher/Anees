package com.example.anees.ui.screens.settings

import android.Manifest
import android.content.Intent
import android.os.Build
import android.provider.Settings
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.anees.enums.AppPermission
import com.example.anees.enums.AzanRecitersEnum
import com.example.anees.enums.FajrRecitersEnum
import com.example.anees.enums.ZekirIntervalsEnum
import com.example.anees.ui.dialog.rememberPermissionRequestHandler
import com.example.anees.ui.screens.hadith.components.ScreenTitle
import com.example.anees.ui.screens.radio.components.ScreenBackground
import com.example.anees.ui.screens.settings.Component.SettingDropdownMenu
import com.example.anees.ui.screens.settings.Component.SettingSection
import com.example.anees.ui.screens.settings.Component.SettingSwitchRow
import com.example.anees.workers.setNotification
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Preview(showBackground = true)
@Composable
fun SettingsScreen(
    onBackClick: () -> Unit = {},
    onAzanViewClick: () -> Unit = {},
    onFajarClick: () -> Unit = {},
) {

    val systemUiController = rememberSystemUiController()

    SideEffect {
        systemUiController.setStatusBarColor(
            color = Color.Transparent,
            darkIcons = true
        )
    }

    val viewModel: SettingsViewModel = hiltViewModel()
    val textColor = Color.Black
    val switchColor = Color(0xFF4CAF50)
    val sectionColor = Color(0xFFFAF9F6)

    val context = LocalContext.current

    var zekrNotificationState = viewModel.zekrNotificationState.collectAsStateWithLifecycle()
    var AzanNotificationState = viewModel.azanNotificationState.collectAsStateWithLifecycle()
    var selectedFajr = viewModel.currentFajrReciter.collectAsStateWithLifecycle()
    var selectedInterval = viewModel.currentZekirInterval.collectAsStateWithLifecycle()
    var selectedAzan = viewModel.currentAzanReciter.collectAsStateWithLifecycle()

    val intervals = ZekirIntervalsEnum.entries.map { it.label }
    val AzanList = AzanRecitersEnum.entries.map { it.label }
    val fajrList = FajrRecitersEnum.entries.map { it.label }

    val notificationPermissionHandler = rememberPermissionRequestHandler(
        permission = Manifest.permission.POST_NOTIFICATIONS,
        title = AppPermission.Notification.title,
        message = AppPermission.Notification.message,
        rationaleTitle = "إذن الإشعارات مطلوب",
        rationaleMessage = "تم رفض إذن الإشعارات مسبقًا. الرجاء تفعيله يدويًا من إعدادات التطبيق.",
        onGranted = {
            setNotification(context)
            viewModel.updateZekirNotificationState(true)
        },
        onDeclined = { viewModel.updateZekirNotificationState(false) },
        permissionToBeChecked = AppPermission.Notification
    )

    val showOverlayPermissionDialog = remember { mutableStateOf(false) }

    val requestOverlayPermission = {    // TODO AHMED SAAD
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(context)) {
                showOverlayPermissionDialog.value = true
            } else {
                viewModel.updateAzanNotificationState(true)
            }
        }
    }

    val lifecycleOwner = LocalLifecycleOwner.current

    // Observe lifecycle events
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                // TODO Ahmed Saad
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        ScreenBackground()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(vertical = 24.dp)
                .verticalScroll(rememberScrollState())
        ) {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                ScreenTitle(title = "الاعدادات", onBackClick = onBackClick, size = 24)
            }

            SettingSection(title = "إعدادات الأذكار", color = sectionColor) {
                SettingSwitchRow(
                    title = "تفعيل إشعارات الأذكار",
                    isChecked = zekrNotificationState.value,
                    color = switchColor,
                    onCheckedChange = { isChecked ->
                        if (isChecked) {
                            notificationPermissionHandler()
                        } else {
                            viewModel.updateZekirNotificationState(false)
                        }
                    }
                )

                SettingDropdownMenu(
                    title = "المدة بين الاشعارات",
                    options = intervals,
                    textColor = textColor,
                    selectedOption = ZekirIntervalsEnum.getLabelByValue(selectedInterval.value),
                    onOptionSelected = {
                        viewModel.updateCurrentZekirInterval(ZekirIntervalsEnum.getValueByLabel(it))
                    }
                )
            }

            SettingSection(title = "إعدادات الأذان", color = sectionColor) {
                SettingSwitchRow(
                    title = "تفعيل تنبيهات الأذان",
                    isChecked = AzanNotificationState.value,
                    color = switchColor,
                    onCheckedChange = { isChecked ->
                        if (isChecked) {
                            // When Azan notifications are enabled, request overlay permission
                            requestOverlayPermission()
                        } else {
                            viewModel.updateAzanNotificationState(false)
                        }
                    }
                )

                SettingDropdownMenu(
                    title = "أذان صلاة الفجر",
                    options = fajrList,
                    selectedOption = selectedFajr.value,
                    textColor = textColor,
                    onOptionSelected = {
                        viewModel.updateCurrentFajrReciter(it)
                    },
                    trailingIcon = {
                        IconButton(onClick = onFajarClick) {
                            Icon(
                                imageVector = Icons.Default.RemoveRedEye,
                                contentDescription = null,
                                tint = switchColor,
                            )
                        }
                    }
                )

                SettingDropdownMenu(
                    title = "أذان باقي الصلوات",
                    options = AzanList,
                    selectedOption = selectedAzan.value,
                    textColor = textColor,
                    onOptionSelected = {
                        viewModel.updateCurrentAzanReciter(it)
                    },
                    trailingIcon = {
                        IconButton(onClick = onAzanViewClick) {
                            Icon(
                                imageVector = Icons.Default.RemoveRedEye,
                                contentDescription = null,
                                tint = switchColor,
                            )
                        }
                    }
                )
            }

            Spacer(Modifier.height(80.dp))

        }
    }

    if (showOverlayPermissionDialog.value) {
        AlertDialog(
            onDismissRequest = { showOverlayPermissionDialog.value = false },
            title = { Text("السماح بعرض التنبيهات فوق التطبيقات") },
            text = {
                Text("يرجى السماح للتطبيق بعرض التنبيهات فوق التطبيقات الأخرى من خلال الإعدادات.")
            },
            confirmButton = {
                TextButton(onClick = {
                    showOverlayPermissionDialog.value = false
                    val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION).apply {
                    }
                    context.startActivity(intent)
                }) {
                    Text("فتح الإعدادات")
                }
            },
            dismissButton = {
                TextButton(onClick = { showOverlayPermissionDialog.value = false }) {
                    Text("إلغاء")
                }
            }
        )
    }

//    if (showPermissionDialog.value) {
//        AlertDialog(
//            onDismissRequest = { showPermissionDialog.value = false },
//            title = { Text("السماح بالإشعارات") },
//            text = {
//                Text()
//            },
//            confirmButton = {
//                TextButton(onClick = {
//                    showPermissionDialog.value = false
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//                        val intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS).apply {
//                            putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
//                        }
//                        context.startActivity(intent)
//                    } else {
//                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
//                            data = Uri.fromParts("package", context.packageName, null)
//                        }
//                        context.startActivity(intent)
//                    }
//                }) {
//                    Text("فتح الإعدادات")
//                }
//            },
//            dismissButton = {
//                TextButton(onClick = { showPermissionDialog.value = false }) {
//                    Text("إلغاء")
//                }
//            }
//        )
//    }
}
