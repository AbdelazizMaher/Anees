package com.muslim.anees.data.model


import com.google.gson.annotations.SerializedName

data class EditionResponse(
    @SerializedName("hadiths")
    val hadiths: List<Hadith>,
    @SerializedName("metadata")
    val metadata: Metadata
) {
    data class Hadith(
        @SerializedName("arabicnumber")
        val arabicnumber: Double,
        @SerializedName("grades")
        val grades: List<Grade>,
        @SerializedName("hadithnumber")
        val hadithnumber: Double,
        @SerializedName("reference")
        val reference: Reference,
        @SerializedName("text")
        val text: String
    ) {
        data class Grade(
            @SerializedName("grade")
            val grade: String,
            @SerializedName("name")
            val name: String
        )

        data class Reference(
            @SerializedName("book")
            val book: Int,
            @SerializedName("hadith")
            val hadith: Int
        )
    }

    data class Metadata(
        @SerializedName("name")
        val name: String,
        @SerializedName("section_details")
        val sectionDetails: SectionDetails,
        @SerializedName("sections")
        val sections: Sections
    ) {
        data class SectionDetails(
            @SerializedName("0")
            val x0: SectionDetail,
            @SerializedName("1")
            val x1: SectionDetail,
            @SerializedName("10")
            val x10: SectionDetail,
            @SerializedName("11")
            val x11: SectionDetail,
            @SerializedName("12")
            val x12: SectionDetail,
            @SerializedName("13")
            val x13: SectionDetail,
            @SerializedName("14")
            val x14: SectionDetail,
            @SerializedName("15")
            val x15: SectionDetail,
            @SerializedName("16")
            val x16: SectionDetail,
            @SerializedName("17")
            val x17: SectionDetail,
            @SerializedName("18")
            val x18: SectionDetail,
            @SerializedName("19")
            val x19: SectionDetail,
            @SerializedName("2")
            val x2: SectionDetail,
            @SerializedName("20")
            val x20: SectionDetail,
            @SerializedName("21")
            val x21: SectionDetail,
            @SerializedName("22")
            val x22: SectionDetail,
            @SerializedName("23")
            val x23: SectionDetail,
            @SerializedName("24")
            val x24: SectionDetail,
            @SerializedName("25")
            val x25: SectionDetail,
            @SerializedName("26")
            val x26: SectionDetail,
            @SerializedName("27")
            val x27: SectionDetail,
            @SerializedName("28")
            val x28: SectionDetail,
            @SerializedName("29")
            val x29: SectionDetail,
            @SerializedName("3")
            val x3: SectionDetail,
            @SerializedName("30")
            val x30: SectionDetail,
            @SerializedName("31")
            val x31: SectionDetail,
            @SerializedName("32")
            val x32: SectionDetail,
            @SerializedName("33")
            val x33: SectionDetail,
            @SerializedName("34")
            val x34: SectionDetail,
            @SerializedName("35")
            val x35: SectionDetail,
            @SerializedName("36")
            val x36: SectionDetail,
            @SerializedName("37")
            val x37: SectionDetail,
            @SerializedName("38")
            val x38: SectionDetail,
            @SerializedName("39")
            val x39: SectionDetail,
            @SerializedName("4")
            val x4: SectionDetail,
            @SerializedName("40")
            val x40: SectionDetail,
            @SerializedName("41")
            val x41: SectionDetail,
            @SerializedName("42")
            val x42: SectionDetail,
            @SerializedName("43")
            val x43: SectionDetail,
            @SerializedName("44")
            val x44: SectionDetail,
            @SerializedName("45")
            val x45: SectionDetail,
            @SerializedName("46")
            val x46: SectionDetail,
            @SerializedName("47")
            val x47: SectionDetail,
            @SerializedName("48")
            val x48: SectionDetail,
            @SerializedName("49")
            val x49: SectionDetail,
            @SerializedName("5")
            val x5: SectionDetail,
            @SerializedName("50")
            val x50: SectionDetail,
            @SerializedName("51")
            val x51: SectionDetail,
            @SerializedName("52")
            val x52: SectionDetail,
            @SerializedName("53")
            val x53: SectionDetail,
            @SerializedName("54")
            val x54: SectionDetail,
            @SerializedName("55")
            val x55: SectionDetail,
            @SerializedName("56")
            val x56: SectionDetail,
            @SerializedName("57")
            val x57: SectionDetail,
            @SerializedName("58")
            val x58: SectionDetail,
            @SerializedName("59")
            val x59: SectionDetail,
            @SerializedName("6")
            val x6: SectionDetail,
            @SerializedName("60")
            val x60: SectionDetail,
            @SerializedName("61")
            val x61: SectionDetail,
            @SerializedName("7")
            val x7: SectionDetail,
            @SerializedName("8")
            val x8: SectionDetail,
            @SerializedName("9")
            val x9: SectionDetail
        ) {
            data class SectionDetail(
                @SerializedName("arabicnumber_first")
                val arabicnumberFirst: Double,
                @SerializedName("arabicnumber_last")
                val arabicnumberLast: Double,
                @SerializedName("hadithnumber_first")
                val hadithnumberFirst: Double,
                @SerializedName("hadithnumber_last")
                val hadithnumberLast: Double
            )
        }

        data class Sections(
            @SerializedName("0")
            val x0: String,
            @SerializedName("1")
            val x1: String,
            @SerializedName("10")
            val x10: String,
            @SerializedName("11")
            val x11: String,
            @SerializedName("12")
            val x12: String,
            @SerializedName("13")
            val x13: String,
            @SerializedName("14")
            val x14: String,
            @SerializedName("15")
            val x15: String,
            @SerializedName("16")
            val x16: String,
            @SerializedName("17")
            val x17: String,
            @SerializedName("18")
            val x18: String,
            @SerializedName("19")
            val x19: String,
            @SerializedName("2")
            val x2: String,
            @SerializedName("20")
            val x20: String,
            @SerializedName("21")
            val x21: String,
            @SerializedName("22")
            val x22: String,
            @SerializedName("23")
            val x23: String,
            @SerializedName("24")
            val x24: String,
            @SerializedName("25")
            val x25: String,
            @SerializedName("26")
            val x26: String,
            @SerializedName("27")
            val x27: String,
            @SerializedName("28")
            val x28: String,
            @SerializedName("29")
            val x29: String,
            @SerializedName("3")
            val x3: String,
            @SerializedName("30")
            val x30: String,
            @SerializedName("31")
            val x31: String,
            @SerializedName("32")
            val x32: String,
            @SerializedName("33")
            val x33: String,
            @SerializedName("34")
            val x34: String,
            @SerializedName("35")
            val x35: String,
            @SerializedName("36")
            val x36: String,
            @SerializedName("37")
            val x37: String,
            @SerializedName("38")
            val x38: String,
            @SerializedName("39")
            val x39: String,
            @SerializedName("4")
            val x4: String,
            @SerializedName("40")
            val x40: String,
            @SerializedName("41")
            val x41: String,
            @SerializedName("42")
            val x42: String,
            @SerializedName("43")
            val x43: String,
            @SerializedName("44")
            val x44: String,
            @SerializedName("45")
            val x45: String,
            @SerializedName("46")
            val x46: String,
            @SerializedName("47")
            val x47: String,
            @SerializedName("48")
            val x48: String,
            @SerializedName("49")
            val x49: String,
            @SerializedName("5")
            val x5: String,
            @SerializedName("50")
            val x50: String,
            @SerializedName("51")
            val x51: String,
            @SerializedName("52")
            val x52: String,
            @SerializedName("53")
            val x53: String,
            @SerializedName("54")
            val x54: String,
            @SerializedName("55")
            val x55: String,
            @SerializedName("56")
            val x56: String,
            @SerializedName("57")
            val x57: String,
            @SerializedName("58")
            val x58: String,
            @SerializedName("59")
            val x59: String,
            @SerializedName("6")
            val x6: String,
            @SerializedName("60")
            val x60: String,
            @SerializedName("61")
            val x61: String,
            @SerializedName("7")
            val x7: String,
            @SerializedName("8")
            val x8: String,
            @SerializedName("9")
            val x9: String
        )
    }
}