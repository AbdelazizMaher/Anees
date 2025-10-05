package com.example.anees.data.local

import com.example.anees.data.model.AzkarEntity
import com.example.anees.data.model.HadithEntity
import com.example.anees.data.model.Sebiha
import com.example.anees.data.model.TafsierModel
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {

    // --- SharedPreferences ---
    fun saveData(key: String, value: Any)
    fun <T> fetchData(key: String, defaultValue: T): T

    // --- Sebiha ---
    suspend fun insertSebiha(sebiha: Sebiha)
    fun getSebiha(): Flow<Sebiha>

    // --- Tafsir ---
    suspend fun insertTafsir(tafsir: TafsierModel)
    fun getTafsir(id: Int): Flow<TafsierModel?>

    // --- Mahafogat (Azkar / Ad3ya) ---

    // Azkar
    suspend fun insertAzkar(azkar: AzkarEntity)
    suspend fun deleteAzkar(azkar: AzkarEntity)
    suspend fun isAzkarSaved(category: String): Boolean
    fun getSavedAzkarFlow(): Flow<List<AzkarEntity>>

    // Hadith
    suspend fun insertHadith(hadith: HadithEntity)
    suspend fun deleteHadith(hadith: HadithEntity)
    suspend fun isHadithSaved(title: String): Boolean
    fun getSavedHadithFlow(): Flow<List<HadithEntity>>

}