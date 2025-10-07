package com.muslim.anees.data.model

class adhkar : ArrayList<adhkarItem>()

data class adhkarItem(
    val array: List<ZekrModelItem>,
    val audio: String,
    val category: String,
    val filename: String,
    val id: Int
)

data class ZekrModelItem(
    val audio: String,
    val count: Int,
    val filename: String,
    val id: Int,
    val text: String
)