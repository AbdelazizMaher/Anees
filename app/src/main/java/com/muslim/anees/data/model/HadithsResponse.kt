package com.muslim.anees.data.model

data class HadithsResponse(
    val hadiths: List<Hadith>,
    val metadata: Metadata
) {
    data class Hadith(
        val arabicnumber: Int,
        val grades: List<Any?>,
        val hadithnumber: Int,
        val reference: Reference,
        val text: String
    ) {
        data class Reference(
            val book: Int,
            val hadith: Int
        )
    }

    data class Metadata(
        val name: String,
        val section: Section,
        val section_detail: SectionDetail
    ) {
        data class Section(
            val `97`: String
        )

        data class SectionDetail(
            val `97`: X97
        ) {
            data class X97(
                val arabicnumber_first: Int,
                val arabicnumber_last: Int,
                val hadithnumber_first: Int,
                val hadithnumber_last: Int
            )
        }
    }
}