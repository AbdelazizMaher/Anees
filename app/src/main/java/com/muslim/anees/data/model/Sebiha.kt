package com.muslim.anees.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sebha")
data class Sebiha(
    @PrimaryKey()
    val id: Int=0,
    var count: Int,
    var rounds: Int ,
    var totalRounds: Int,
    var name: String

)