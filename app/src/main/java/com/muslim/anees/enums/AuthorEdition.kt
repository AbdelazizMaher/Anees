package com.muslim.anees.enums

enum class AuthorEdition(
    val apiKey: String,
    val displayNameEn: String,
    val displayNameAr: String
) {
    MUSLIM("muslim", "Imam Muslim", "الإمام مسلم"),
    BUKHARI("bukhari", "Imam Bukhari", "الإمام البخاري"),
    ABU_DAWUD("abudawud", "Abu Dawud", "الإمام أبو داود"),
    TIRMIDHI("tirmidhi", "At-Tirmidhi", "الإمام الترمذي"),
    NASAI("nasai", "An-Nasā’ī", "الإمام النسائي"),
    IBN_MAJAH("ibnmajah", "Ibn Majah", "ابن ماجه"),
    MALIK("malik", "Imam Malik", "الإمام مالك"),
    NAWAWI("nawawi", "Imam Nawawi", "الاربعين النوويه"),
    QUDSI("qudsi", "Hadith Qudsi", "الحديث القدسي");

    companion object {
        fun fromApiKey(key: String): AuthorEdition? =
            entries.find { it.apiKey == key }
    }
}

fun getAuthorsName(online: Boolean): List<AuthorEdition> {
    return if (!online) {
        AuthorEdition.entries.filter {
            it.apiKey != "qudsi" && it.apiKey != "nawawi" && it.apiKey!="tirmidhi"&&it.apiKey!="dehlawi"
        }
    } else {
        AuthorEdition.entries
    }
}