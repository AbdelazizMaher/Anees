package com.muslim.anees.utils

import android.content.Context

fun Context.loadJSONFromAssets(fileName: String): String? {
    return try {
        this.assets.open(fileName).bufferedReader().use { it.readText() }
    } catch (e: Exception) {
        null
    }
}