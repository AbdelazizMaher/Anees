package com.example.anees.utils.extensions

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

fun Long.toArabicTime(): String {
    val date = Date(this)
    val format = SimpleDateFormat("h:mm a", Locale("ar"))
    format.timeZone = TimeZone.getTimeZone("Africa/Cairo")
    return format.format(date)
}
