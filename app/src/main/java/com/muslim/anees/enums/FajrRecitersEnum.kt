package com.muslim.anees.enums

import com.muslim.anees.R

enum class FajrRecitersEnum(val filename: Int, val label: String){
    Tobar(R.raw.tobar_fajr,"آذان الفجر , نصر الدين طوبار"),
    Abdelbaset(R.raw.azan_fajr , "  أذان الفجر , عبد الباسط عبد الصمد"),
    Mashary(R.raw.mashary_fajr , "  أذان الفجر , مشاري بن راشد العفاسي ");

    companion object{
        fun getFileByLabel(label: String): Int {
            return values().find { it.label == label }?.filename ?: Abdelbaset.filename
        }
    }
}