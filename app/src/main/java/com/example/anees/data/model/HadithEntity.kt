package com.example.anees.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mahafogat_hadith")
data class HadithEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String
)
