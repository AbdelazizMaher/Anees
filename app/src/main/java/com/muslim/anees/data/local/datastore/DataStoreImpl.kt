package com.muslim.anees.data.local.datastore

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.muslim.anees.utils.Constants
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreImpl @Inject constructor(
    @ApplicationContext private val context: Context,
): DataStore {

    private val Context.dataStore by preferencesDataStore(Constants.DATASTORE_NAME)

    override suspend fun <T> saveData(
        key: Preferences.Key<T>,
        value: T
    ) {
        context.dataStore.edit { prefs ->
            prefs[key] = value
        }
    }

    override fun <T> getData(
        key: Preferences.Key<T>,
        defaultValue: T
    ): Flow<T> {
        return context.dataStore.data.map { prefs ->
            prefs[key] ?: defaultValue
        }
    }

    override suspend fun clearData() {
        context.dataStore.edit { prefs ->
            prefs.clear()
        }
    }
}