package com.example.anees.utils.extensions

fun String.convertNumbersToArabic(): String {
    val arabicDigits = charArrayOf('٠', '١', '٢', '٣', '٤', '٥', '٦', '٧', '٨', '٩')
    return this.map {
        if (it.isDigit()) arabicDigits[it.digitToInt()] else it
    }.joinToString("")
}