package com.muslim.anees.enums

import com.muslim.anees.R

enum class AzanRecitersEnum(val filename: Int, val label: String){
    Molla(R.raw.molaa_azan , "أذان الحرم المكي , علي بن أحمد ملا"),
    Serihi(R.raw.serihi_azan , "أذان المدينه , عبدالمجيد السريحي "),
    Abdelbaset(R.raw.azan , " أذان القاهرة , عبد الباسط عبد الصمد"),
    Naser(R.raw.azan2 , "أذان الرياض , ناصر القطامي");

    companion object{
        fun getFileByLabel(label: String): Int {
            return values().find { it.label == label }?.filename ?: Abdelbaset.filename
        }
    }
}