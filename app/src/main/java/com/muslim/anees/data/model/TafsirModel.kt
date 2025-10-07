package com.muslim.anees.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tafsier")
data class TafsierModel(
    @PrimaryKey()
    var id: Int,
    val result: List<Result>
)

data class Result(
    val arabic_text: String,
    val aya: String,
    val footnotes: String?,
    val id: String,
    val sura: String,
    val translation: String
)