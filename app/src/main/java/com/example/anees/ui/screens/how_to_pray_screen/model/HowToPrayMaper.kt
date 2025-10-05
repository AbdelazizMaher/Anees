package com.example.anees.ui.screens.how_to_pray_screen.model

import com.example.anees.data.model.HowToPrayDto

fun HowToPrayDto.toHowToPrayPojo(index: Int,isLast:Boolean,link: String): HowToPrayPojo {
    return HowToPrayPojo(
        title = this.title,
        description = this.description,
        image = this.image,
        youtubeLink = link,
        indexOfObj = index,
        isFirst = index==1,
        isLast = isLast,
    )
}