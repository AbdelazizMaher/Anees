package com.muslim.anees.data.repository.audio

import com.muslim.anees.data.model.audio.LastPlayedAudio
import kotlinx.coroutines.flow.Flow

interface AudioRepository {
    suspend fun saveLastPlayedAudio(audio: LastPlayedAudio)
    fun getLastPlayedAudio(): Flow<LastPlayedAudio?>
    suspend fun clearLastPlayedAudio()
}
