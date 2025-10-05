package com.example.anees.data.repository

import com.example.anees.data.model.AzkarEntity
import com.example.anees.data.model.EditionResponse
import com.example.anees.data.model.HadithEntity
import com.example.anees.data.model.Sebiha
import com.example.anees.data.model.TafsierModel
import kotlinx.coroutines.flow.Flow

interface Repository {

    // Sebiha
    suspend fun addSebiha(sebiha: Sebiha)
    fun getSebiha(): Flow<Sebiha>

    // --- Sections (Remote) ---
    suspend fun getAllSections(name: String): Flow<EditionResponse>

    // SharedPreferences
    fun saveData(key: String, value: Any)
    fun <T> fetchData(key: String, defaultValue: T): T

    // Tafsir
    suspend fun getAllTafsier(name: String): Flow<TafsierModel>
    suspend fun addTafsir(tafsir: TafsierModel)
    fun getTafsir(id: Int): Flow<TafsierModel?>


    // --- Azkar ---
    suspend fun insertAzkar(azkar: AzkarEntity)
    suspend fun deleteAzkar(azkar: AzkarEntity)
    suspend fun isAzkarSaved(category: String): Boolean
    suspend fun toggleAzkar(category: String)
    fun getSavedAzkarFlow(): Flow<List<AzkarEntity>>

    // --- Hadith ---
    suspend fun insertHadith(hadith: HadithEntity)
    suspend fun deleteHadith(hadith: HadithEntity)
    suspend fun isHadithSaved(title: String): Boolean
    suspend fun toggleHadith(title: String)
    fun getSavedHadithFlow(): Flow<List<HadithEntity>>
}