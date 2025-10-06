package com.example.anees.utils.date_helper

import android.icu.util.IslamicCalendar
import com.example.anees.utils.extensions.convertNumbersToArabic

object DateHelper {
    fun getTodayHijriDate(): String {
        val calendar = IslamicCalendar()

        val day = calendar.get(IslamicCalendar.DAY_OF_MONTH)
        val monthIndex = calendar.get(IslamicCalendar.MONTH)
        val year = calendar.get(IslamicCalendar.YEAR)

        val hijriMonths = listOf(
            "محرم", "صفر", "ربيع الأول", "ربيع الآخر",
            "جمادى الأولى", "جمادى الآخرة", "رجب", "شعبان",
            "رمضان", "شوال", "ذو القعدة", "ذو الحجة"
        )

        val monthName = hijriMonths.getOrElse(monthIndex) { "غير معروف" }

        return "$day $monthName $year هـ".convertNumbersToArabic()
    }

}