package com.muslim.anees.data.local.sharedpreference

import android.content.Context
import android.content.SharedPreferences
import com.abdok.atmosphere.data.local.sharedPreference.ISharedPreferences
import com.muslim.anees.utils.Constants
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPreferencesImpl @Inject constructor(@ApplicationContext context: Context) :
    ISharedPreferences {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(Constants.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)


    override fun <T> saveData(key: String, value: T) {
        with(sharedPreferences.edit()) {
            when (value) {
                is String -> putString(key, value)
                is Int -> putInt(key, value)
                is Boolean -> putBoolean(key, value)
                is Float -> putFloat(key, value)
                is Long -> putLong(key, value)
                is Double -> putFloat(key, value.toFloat())
                else -> throw IllegalArgumentException("Unsupported type")
            }
            apply()
        }
    }

    override fun <T> fetchData(key: String, defaultValue: T): T {
        @Suppress("UNCHECKED_CAST")
        return when (defaultValue) {
            is String -> sharedPreferences.getString(key, defaultValue) as T
            is Int -> sharedPreferences.getInt(key, defaultValue) as T
            is Boolean -> sharedPreferences.getBoolean(key, defaultValue) as T
            is Float -> sharedPreferences.getFloat(key, defaultValue) as T
            is Long -> sharedPreferences.getLong(key, defaultValue) as T
            is Double -> sharedPreferences.getFloat(key, defaultValue.toFloat()).toDouble() as T
            else -> throw IllegalArgumentException("Unsupported type")
        }
    }
}
