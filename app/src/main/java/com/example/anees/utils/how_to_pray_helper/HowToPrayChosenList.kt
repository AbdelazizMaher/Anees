package com.example.anees.utils.how_to_pray_helper

import com.example.anees.data.model.HowToPrayDto


fun getHowToPrayChosenList(chosenIndex: String): List<HowToPrayDto> {
    return when (chosenIndex) {
        "الوضوء" -> wodoaList
        "الصلاة" -> prayList
        "صلاة الجنازة" -> funeralPrayerList
        "صلاة الاستخارة"-> istikharaPrayerList
        "صلاة الاستسقاء" -> rainPrayerList
        "صلاة العيد" -> eidPrayerList
        "صلاة الخوف" ->fearPrayerList
        "صلاة الكسوف / الخسوف" -> eclipsePrayerList
        else -> emptyList()
    }
}


