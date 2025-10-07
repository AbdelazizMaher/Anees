package com.muslim.anees.enums

import com.muslim.anees.utils.extensions.convertNumbersToArabic

enum class ZekirIntervalsEnum(val value: Int, val label: String) {

    ONE_HOUR(1,"كل ساعة"),
    THREE_HOUR(3,"كل 3 ساعات".convertNumbersToArabic()),
    SIX_HOUR(6,"كل 6 ساعات".convertNumbersToArabic());

    companion object {
        fun getLabelByValue(value: Int): String {
            return values().find { it.value == value }?.label ?: ONE_HOUR.label
        }

        fun getValueByLabel(label: String): Int {
            return values().find { it.label == label }?.value ?: ONE_HOUR.value
        }
    }


}