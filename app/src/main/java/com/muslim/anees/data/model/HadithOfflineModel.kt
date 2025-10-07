package com.muslim.anees.data.model

data class HadithOffline(
    val hadiths: List<Hadith>,
    val metadata: Metadata
){
    data class Hadith(
        val hadithnumber: Int,
        val text: String
    )

    data class Metadata(
        val name: String,
        val section: Section,
        val section_detail: SectionDetail
    )

    data class Section(
        val `15`: String
    )

    data class SectionDetail(
        val `15`: X15
    )

    data class X15(
        val arabicnumber_first: Int,
        val arabicnumber_last: Int,
        val hadithnumber_first: Int,
        val hadithnumber_last: Int
    )
}
//
//fun HadithOffline.Hadith.toHadith(): EditionResponse.Hadith {
//    return EditionResponse.Hadith(
//        arabicnumber = hadithnumber.toDouble(),
//        grades = emptyList(),
//        hadithnumber = hadithnumber.toDouble(),
//        reference = EditionResponse.Hadith.Reference(
//            book = 0,
//            hadith = hadithnumber
//        ),
//        text = text
//    )
//}



fun HadithOffline.Hadith.toHadith(): EditionResponse.Hadith {
    return EditionResponse.Hadith(
        arabicnumber = hadithnumber.toDouble(), // Keep it as double if required
        grades = emptyList(), // If you plan to include grades, this can be adjusted
        hadithnumber = hadithnumber.toDouble(), // or keep it as an Int if no decimal is required
        reference = EditionResponse.Hadith.Reference(
            book = 0, // Assuming the book reference is always 0, but this can be customized
            hadith = hadithnumber.toInt() // Using hadithnumber as reference
        ),
        text = text // Ensure text is correctly populated and non-null
    )
}
