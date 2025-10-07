package com.muslim.anees.utils.hadith_helper

import com.muslim.anees.R
import com.muslim.anees.enums.AuthorEdition

object AuthorAssets {
    private val authorImages = mapOf(
        AuthorEdition.ABU_DAWUD to R.drawable.abu_dawud,
        AuthorEdition.BUKHARI to R.drawable.bukhari,
        AuthorEdition.IBN_MAJAH to R.drawable.ibn_majah,
        AuthorEdition.MALIK to R.drawable.malik,
        AuthorEdition.MUSLIM to R.drawable.muslim,
        AuthorEdition.NASAI to R.drawable.nasai,
        AuthorEdition.NAWAWI to R.drawable.nawawi,
        AuthorEdition.QUDSI to R.drawable.qudsi,
        AuthorEdition.TIRMIDHI to R.drawable.tirmidhi
    )

    fun getImage(author: AuthorEdition): Int {
        return authorImages[author] ?: R.drawable.tirmidhi
    }
}