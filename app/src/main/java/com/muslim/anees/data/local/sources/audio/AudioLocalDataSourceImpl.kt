package com.muslim.anees.data.local.sources.audio

import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.muslim.anees.data.local.datastore.DataStore
import com.muslim.anees.data.model.audio.LastPlayedAudio
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class AudioLocalDataSourceImpl @Inject constructor(
    private val dataStore: DataStore
): AudioLocalDataSource {
    override suspend fun saveLastPlayedAudio(audio: LastPlayedAudio) {
        dataStore.saveData(PreferencesKeys.LAST_PLAYED_AUDIO_TITLE, audio.title)
        dataStore.saveData(PreferencesKeys.LAST_PLAYED_AUDIO_RECITER, audio.reciter)
        dataStore.saveData(PreferencesKeys.LAST_PLAYED_AUDIO_RECITER_IMAGE, audio.reciterImage)
        dataStore.saveData(PreferencesKeys.LAST_PLAYED_AUDIO_PROGRESS, audio.progress)
    }

    override fun getLastPlayedAudio(): Flow<LastPlayedAudio?> {
        val title = dataStore.getData(PreferencesKeys.LAST_PLAYED_AUDIO_TITLE, "")
        val reciter = dataStore.getData(PreferencesKeys.LAST_PLAYED_AUDIO_RECITER, "")
        val reciterImage = dataStore.getData(PreferencesKeys.LAST_PLAYED_AUDIO_RECITER_IMAGE, "")
        val progress = dataStore.getData(PreferencesKeys.LAST_PLAYED_AUDIO_PROGRESS, 0.0F)

        return combine(title, reciter, reciterImage, progress) { title, reciter, reciterImage, progress ->
            if (title.isEmpty() || reciter.isEmpty()) {
                null
            } else {
                LastPlayedAudio(reciter, reciterImage, title, progress)
            }
        }
    }

    override suspend fun clearLastPlayedAudio() {
        dataStore.clearData()
    }

    object PreferencesKeys {
        val LAST_PLAYED_AUDIO_TITLE = stringPreferencesKey("LAST_PLAYED_AUDIO_TITLE")
        val LAST_PLAYED_AUDIO_RECITER = stringPreferencesKey("LAST_PLAYED_AUDIO_RECITER")
        val LAST_PLAYED_AUDIO_RECITER_IMAGE = stringPreferencesKey("LAST_PLAYED_AUDIO_RECITER_IMAGE")
        val LAST_PLAYED_AUDIO_PROGRESS = floatPreferencesKey("LAST_PLAYED_AUDIO_PROGRESS")
    }
}