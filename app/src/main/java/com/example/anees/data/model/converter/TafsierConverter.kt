package com.example.anees.data.model.converter

import androidx.room.TypeConverter
import com.example.anees.data.model.Result
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class TafsierConverter {
    @TypeConverter
    fun fromResultList(value: List<Result>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toResultList(value: String): List<Result> {
        val listType = object : TypeToken<List<Result>>() {}.type
        return Gson().fromJson(value, listType)
    }
}