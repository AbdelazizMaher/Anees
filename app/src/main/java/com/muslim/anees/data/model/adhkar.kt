package com.muslim.anees.data.model


data class AdhkarItem(
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