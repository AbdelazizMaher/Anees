package com.muslim.anees.utils.names_of_allah_helper

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.muslim.anees.data.model.NamesOfAllahModelItem
import com.muslim.anees.utils.Constants
import com.muslim.anees.utils.loadJSONFromAssets

fun getAllNames(context: Context): List<NamesOfAllahModelItem>{
    val jsonString = context.loadJSONFromAssets(Constants.NAMES_OF_ALLAH) ?: return emptyList()
    return try {
        val gson = Gson()
        val type = object : TypeToken<List<NamesOfAllahModelItem>>() {}.type
        gson.fromJson(jsonString, type)
    } catch (e: Exception) {
        emptyList()
    }
}