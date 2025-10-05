package com.example.anees.ui.screens.how_to_pray_screen.model

data class HowToPrayPojo(
    val title: String,
    val description: String,
    val image: String,
    val indexOfObj: Int,
    val isFirst: Boolean,
    val isLast: Boolean,
    var youtubeLink: String?,
)