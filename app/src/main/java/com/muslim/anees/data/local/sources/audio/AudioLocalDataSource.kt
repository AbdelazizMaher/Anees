package com.muslim.anees.data.local.sources.audio

import com.muslim.anees.data.model.audio.LastPlayedAudio
import kotlinx.coroutines.flow.Flow

interface AudioLocalDataSource {
    suspend fun saveLastPlayedAudio(audio: LastPlayedAudio)
    fun getLastPlayedAudio(): Flow<LastPlayedAudio?>
    suspend fun clearLastPlayedAudio()
}

