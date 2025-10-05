package com.example.anees.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.anees.data.model.AzkarEntity
import com.example.anees.data.model.HadithEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MahafogatDao {

    // Azkar
    @Insert
    suspend fun addAzkar(azkar: AzkarEntity)

    @Query("DELETE FROM mahafogat_azkar WHERE category = :category")
    suspend fun deleteAzkar(category: String)

    @Query("SELECT EXISTS(SELECT 1 FROM mahafogat_azkar WHERE category = :category)")
    suspend fun isAzkarSaved(category: String): Boolean

    @Query("SELECT * FROM mahafogat_azkar")
    fun getAllSavedAzkarFlow(): Flow<List<AzkarEntity>>

    // Hadith
    @Insert
    suspend fun addHadith(hadith: HadithEntity)

    @Query("DELETE FROM mahafogat_hadith WHERE title = :title")
    suspend fun deleteHadith(title: String)

    @Query("SELECT EXISTS(SELECT 1 FROM mahafogat_hadith WHERE title = :title)")
    suspend fun isHadithSaved(title: String): Boolean

    @Query("SELECT * FROM mahafogat_hadith")
    fun getAllSavedHadithFlow(): Flow<List<HadithEntity>>

}
