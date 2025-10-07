package com.muslim.anees.utils.hisn_almuslim_helper

import android.content.Context
import com.google.gson.Gson
import org.json.JSONObject

object HisnAlMuslimHelper {
    private fun loadJsonString(context: Context, fileName: String): String {
        return context.assets.open(fileName).bufferedReader().use { it.readText() }
    }

    fun getTitles(context: Context, fileName: String): List<String> {
        val jsonString = loadJsonString(context, fileName)
        val jsonObject = JSONObject(jsonString)
        val titles = mutableListOf<String>()
        val keys = jsonObject.keys()
        while (keys.hasNext()) {
            titles.add(keys.next())
        }
        return titles
    }

    fun getSectionContent(context: Context, fileName: String, title: String): Pair<List<String>, List<String>> {
        val jsonString = loadJsonString(context, fileName)
        val jsonObject = JSONObject(jsonString)
        val section = jsonObject.getJSONObject(title)
        val gson = Gson()

        val textArray = gson.fromJson(section.getJSONArray("text").toString(), Array<String>::class.java).toList()
        val footnoteArray = gson.fromJson(section.getJSONArray("footnote").toString(), Array<String>::class.java).toList()

        return Pair(textArray, footnoteArray)
    }
}