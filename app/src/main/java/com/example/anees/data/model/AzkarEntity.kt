package com.example.anees.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mahafogat_azkar")
data class AzkarEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val category: String
)
