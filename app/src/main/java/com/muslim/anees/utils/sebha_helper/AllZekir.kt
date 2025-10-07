package com.muslim.anees.utils.sebha_helper

import com.muslim.anees.data.model.Zekir

val azkarList = listOf(
    Zekir(
        arabicName = "سُبْحَانَ اللَّهِ",
        englishName = "Subhana Allah",
        spanishName = "Gloria a Dios"
    ),
    Zekir(
        arabicName = "سُبْحَانَ اللَّهِ وَبِحَمْدِهِ",
        englishName = "Glory be to Allah and praise Him",
        spanishName = "Gloria a Alá y alabado sea"
    ),
    Zekir(
        arabicName = "سُبْحَانَ اللَّهِ وَالْحَمْدُ لِلَّهِ",
        englishName = "Glory be to Allah and all praise is due to Allah",
        spanishName = "Gloria a Alá y toda alabanza a Él"
    ),
    Zekir(
        arabicName = "سُبْحَانَ اللَّهِ العَظِيمِ وَبِحَمْدِهِ",
        englishName = "Glory be to the Great Allah and praise Him",
        spanishName = "Gloria al Gran Alá y alabado sea"
    ),
    Zekir(
        arabicName = "سُبْحَانَ اللَّهِ وَبِحَمْدِهِ ، سُبْحَانَ اللَّهِ الْعَظِيمِ",
        englishName = "Glory be to Allah and praise Him, Glory be to the Great Allah",
        spanishName = "Gloria a Alá y alabado sea, gloria al Gran Alá"
    ),
    Zekir(
        arabicName = "لَا حَوْلَ وَلا قُوَّةَ إِلَّا بِاللَّهِ",
        englishName = "There is no power and no strength except with Allah",
        spanishName = "No hay poder ni fuerza sino en Alá"
    ),
    Zekir(
        arabicName = "الْحَمْدُ لِلّهِ رَبِّ الْعَالَمِينَ",
        englishName = "Praise be to Allah, the Lord of the Worlds",
        spanishName = "Alabanza a Alá, Señor de los mundos"
    ),
    Zekir(
        arabicName = "اللَّهُم صَلِّ وَسَلِّم وَبَارِك عَلَى سَيِّدِنَا مُحَمَّد",
        englishName = "O Allah, send blessings and peace upon our master Muhammad",
        spanishName = "Oh Alá, bendice y da paz a nuestro maestro Muhammad"
    ),
    Zekir(
        arabicName = "أَسْتَغْفِرُ اللَّهَ",
        englishName = "I seek forgiveness from Allah",
        spanishName = "Pido perdón a Alá"
    ),
    Zekir(
        arabicName = "سُبْحَانَ اللَّهِ، وَالْحَمْدُ لِلَّهِ، وَلَا إِلَهَ إِلَّا اللَّهُ، وَاللَّهُ أَكْبَرُ",
        englishName = "Glory be to Allah, and praise be to Allah, and there is no god but Allah, and Allah is the Greatest",
        spanishName = "Gloria a Alá, alabanza a Alá, no hay dios salvo Alá, y Alá es el más grande"
    ),
    Zekir(
        arabicName = "لَا إِلَهَ إِلَّا اللَّهُ",
        englishName = "There is no god but Allah",
        spanishName = "No hay dios sino Alá"
    ),
    Zekir(
        arabicName = "اللَّهُ أَكْبَرُ",
        englishName = "Allah is the Greatest",
        spanishName = "Alá es el más grande"
    ),
    Zekir(
        arabicName = "الْحَمْدُ لِلَّهِ حَمْدًا كَثِيرًا طَيِّبًا مُبَارَكًا فِيهِ",
        englishName = "Praise be to Allah, abundant, good, and blessed praise",
        spanishName = "Alabanza a Alá, abundante, buena y bendecida"
    ),
    Zekir(
        arabicName = "لَا إِلَهَ إِلَّا أَنْتَ سُبْحَانَكَ إِنِّي كُنتُ مِنَ الظَّالِمِينَ",
        englishName = "There is no deity except You; glory be to You. Indeed, I was among the wrongdoers",
        spanishName = "No hay deidad sino Tú; gloria a Ti. En verdad, yo era de los injustos"
    ),
    Zekir(
        arabicName = "لَا إِلَهَ إِلَّا اللَّهُ وَحْدَهُ لَا شَرِيكَ لَهُ، لَهُ الْمُلْكُ وَلَهُ الْحَمْدُ وَهُوَ عَلَىٰ كُلِّ شَيْءٍ قَدِيرٌ",
        englishName = "There is no deity but Allah, alone without partner. His is the dominion and His is the praise, and He is capable of all things",
        spanishName = "No hay más dios que Alá, solo, sin asociado. Suyo es el reino y la alabanza, y Él es capaz de todas las cosas"
    )
)

fun getRandomZekir(): Zekir {
    return azkarList.random()
}