package com.example.anees.utils.extensions

fun String.convertNumbersToArabic(): String {
    val arabicDigits = charArrayOf('٠', '١', '٢', '٣', '٤', '٥', '٦', '٧', '٨', '٩')
    val englishDigits = charArrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9')

    return this.map {
        if (it.isDigit()) arabicDigits[it.digitToInt()] else it
    }.joinToString("")
}