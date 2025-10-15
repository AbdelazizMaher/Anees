package com.muslim.anees.utils.azkar_helper

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.muslim.anees.data.model.ZekrModelItem
import com.muslim.anees.data.model.AdhkarItem
import com.muslim.anees.utils.Constants
import com.muslim.anees.utils.loadJSONFromAssets

object AzkarUtils{
    fun parseAdhkar(context: Context): List<AdhkarItem> {
        val jsonString = context.loadJSONFromAssets(Constants.AZKAR_FILE_NAME) ?: return emptyList()
        return try {
            val gson = Gson()
            val type = object : TypeToken<List<AdhkarItem>>() {}.type
            gson.fromJson(jsonString, type)
        } catch (e: Exception) {
            emptyList()
        }
    }

    fun getAdhkarCategories(azkarList: List<AdhkarItem>): List<String> {
        return azkarList.map { it.category }.distinct()
    }

    fun getAzkarByCategory(azkarList: List<AdhkarItem>, category: String): List<ZekrModelItem> {
        return azkarList
            .filter { it.category == category }
            .flatMap { it.array }
            .sortedBy { it.count }

    }

}
