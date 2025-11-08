package com.muslim.anees.data.repository.audio

import com.muslim.anees.data.local.sources.audio.AudioLocalDataSource
import com.muslim.anees.data.model.audio.LastPlayedAudio
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AudioRepositoryImpl @Inject constructor(
    private val localDataSource: AudioLocalDataSource,
): AudioRepository {
    override suspend fun saveLastPlayedAudio(audio: LastPlayedAudio) {
        localDataSource.saveLastPlayedAudio(audio)
    }

    override fun getLastPlayedAudio(): Flow<LastPlayedAudio?> {
        return localDataSource.getLastPlayedAudio()
    }

    override suspend fun clearLastPlayedAudio() {
        localDataSource.clearLastPlayedAudio()
    }
}