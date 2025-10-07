package com.muslim.anees.utils.hadith_helper.offline_hadith

import android.content.Context
import com.muslim.anees.data.model.HadithOffline
import com.muslim.anees.enums.AuthorEdition
import com.muslim.anees.utils.loadJSONFromAssets
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object OfflineHadithHelper{
    fun getAllHadith(context: Context,author: AuthorEdition,selectedEdition: String): HadithOffline? {
        val gson = Gson()
        val path="hadith/${author.apiKey}/${selectedEdition}.json"
        val jsonString = context.loadJSONFromAssets( path) ?: return null

        return try {
            val type = object : TypeToken<HadithOffline>() {}.type
            gson.fromJson(jsonString, type)
        } catch (e: Exception) {
            null
        }
    }

}