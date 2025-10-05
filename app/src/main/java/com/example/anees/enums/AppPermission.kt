package com.example.anees.enums

import android.Manifest
import android.app.AlarmManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.provider.Settings
import androidx.compose.ui.graphics.BlendMode.Companion.Overlay
import androidx.core.content.ContextCompat

enum class AppPermission(val message: String, val title: String) {
    Notification("نحتاج إذنك لإرسال إشعارات الأذان في أوقاتها.", "إذن الإشعارات"),
    Location("نحتاج إذنك لتحديد موقعك بدقة لحساب مواقيت الصلاة الصحيحة.", "إذن الموقع"),
    Overlay("هذا التطبيق يحتاج إلى إذن لعرض المحتوى فوق التطبيقات الأخرى. من دون هذا الإذن، قد لا تعمل بعض الميزات بشكل صحيح (مثل عرض الأذان).", "إذن العرض فوق التطبيقات"),

    Alarm("اسمح للتطبيق بضبط الأذان في الوقت الصحيح حتى عند غلق التطبيق.", "إذن ضبط الأذان");
    fun isGranted(context: Context): Boolean {
        return when (this) {
            Notification -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.POST_NOTIFICATIONS
                    ) == PackageManager.PERMISSION_GRANTED
                } else {
                    true
                }
            }

            Location -> {
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            }

            Overlay -> {
                Settings.canDrawOverlays(context)
            }

            Alarm -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
                    alarmManager.canScheduleExactAlarms()
                } else {
                    true
                }
            }
        }
    }
}