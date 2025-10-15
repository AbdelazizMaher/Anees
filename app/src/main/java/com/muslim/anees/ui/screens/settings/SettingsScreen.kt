package com.muslim.anees.ui.screens.settings

import android.Manifest
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.muslim.anees.enums.AppPermission
import com.muslim.anees.enums.AzanRecitersEnum
import com.muslim.anees.enums.FajrRecitersEnum
import com.muslim.anees.enums.ZekirIntervalsEnum
import com.muslim.anees.ui.dialog.AneesAlertDialog
import com.muslim.anees.ui.dialog.rememberPermissionRequestHandler
import com.muslim.anees.ui.screens.hadith.components.ScreenTitle
import com.muslim.anees.ui.screens.radio.components.ScreenBackground
import com.muslim.anees.ui.screens.settings.Component.SettingDropdownMenu
import com.muslim.anees.ui.screens.settings.Component.SettingSection
import com.muslim.anees.ui.screens.settings.Component.SettingSwitchRow
import com.muslim.anees.utils.extensions.openOverlaySettings
import com.muslim.anees.workers.setNotification

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

    val zekrNotificationState = viewModel.zekrNotificationState.collectAsStateWithLifecycle()
    val azanNotificationState = viewModel.azanNotificationState.collectAsStateWithLifecycle()
    val selectedFajr = viewModel.currentFajrReciter.collectAsStateWithLifecycle()
    val selectedInterval = viewModel.currentZekirInterval.collectAsStateWithLifecycle()
    val selectedAzan = viewModel.currentAzanReciter.collectAsStateWithLifecycle()

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

    val requestOverlayPermission = {
        if (AppPermission.Overlay.isGranted(context)) {
            viewModel.updateAzanNotificationState(true)
        } else showOverlayPermissionDialog.value = true
    }

    if (showOverlayPermissionDialog.value) {
        AneesAlertDialog(
            title = AppPermission.Overlay.title,
            message = AppPermission.Overlay.message,
            onConfirmLabel = "سماح",
            onDismissLabel = "الغاء",
            onConfirm = {
                context.openOverlaySettings()
                showOverlayPermissionDialog.value = false
            },
            onDismiss = { showOverlayPermissionDialog.value = false }
        )
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
                    isChecked = azanNotificationState.value,
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
}
