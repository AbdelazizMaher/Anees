package com.muslim.anees.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.muslim.anees.data.model.Sebiha
import kotlinx.coroutines.flow.Flow

@Dao
interface AneesDao {
    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertSebha(sebha: Sebiha)
    @Query("SELECT * FROM sebha")
    fun getAllSebha(): Flow<Sebiha>

}