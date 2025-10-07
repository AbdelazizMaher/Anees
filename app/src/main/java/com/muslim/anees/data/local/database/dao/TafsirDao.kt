package com.muslim.anees.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.muslim.anees.data.model.TafsierModel
import kotlinx.coroutines.flow.Flow

@Dao
interface TafsirDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTafsir(tafsir: TafsierModel)
    @Query("SELECT * FROM tafsier WHERE id = :id")
     fun getAllTafsir(id: Int): Flow<TafsierModel?>
}