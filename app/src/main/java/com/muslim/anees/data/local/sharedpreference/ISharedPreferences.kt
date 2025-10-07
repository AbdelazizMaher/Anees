package com.abdok.atmosphere.data.local.sharedPreference

interface ISharedPreferences {
    fun <T> saveData(key: String, value: T)
    fun <T> fetchData(key: String, defaultValue: T): T
}