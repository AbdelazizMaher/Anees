package com.muslim.anees.data.local.datastore

import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow

interface DataStore {
    suspend fun<T> saveData(key: Preferences.Key<T>, value: T)
    fun<T> getData(key: Preferences.Key<T>, defaultValue: T): Flow<T>
    suspend fun clearData()
}