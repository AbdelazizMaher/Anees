package com.example.anees.enums

import com.example.anees.R

enum class PrayEnum(val value: String , val icon: Int , val azanBackground: Int , val hadith: String , val author: String) {

    FAJR("صلاة الفجر" , R.drawable.fajr , R.drawable.fajr_azan, "فَضْلُ صَلاةِ الجَمِيعِ علَى صَلاةِ الواحِدِ خَمْسٌ وعِشْرُونَ دَرَجَةً، وتَجْتَمِعُ مَلائِكَةُ اللَّيْلِ ومَلائِكَةُ النَّهارِ في صَلاةِ الصُّبْحِ. يقولُ أبو هُرَيْرَةَ: اقْرَؤُوا إنْ شِئْتُمْ: {وَقُرْآنَ الْفَجْرِ إِنَّ قُرْآنَ الْفَجْرِ كَانَ مَشْهُودًا}",
        "الراوي : أبو هريرة | المحدث : البخاري | المصدر : صحيح البخاري"),
    ZUHR("صلاة الظهر", R.drawable.dhuhr , R.drawable.dhuhr_azan," أنَّ النبيَّ صَلَّى اللهُ عليه وسلَّمَ كانَ لا يَدَعُ أرْبَعًا قَبْلَ الظُّهْرِ، ورَكْعَتَيْنِ قَبْلَ الغَدَاةِ ",
        "الراوي : عائشة أم المؤمنين | المحدث : البخاري | المصدر : صحيح البخاري "),
    ASR("صلاة العصر", R.drawable.asr , R.drawable.asr_azan," كُنَّا مع بُرَيْدَةَ في غَزْوَةٍ في يَومٍ ذِي غَيْمٍ، فَقَالَ: بَكِّرُوا بصَلَاةِ العَصْرِ؛ فإنَّ النبيَّ صَلَّى اللهُ عليه وسلَّمَ قَالَ: مَن تَرَكَ صَلَاةَ العَصْرِ فقَدْ حَبِطَ عَمَلُهُ"
        ,"الراوي : بريدة بن الحصيب الأسلمي | المحدث : البخاري | المصدر : صحيح البخاري"),
    MAGHRIB("صلاة المغرب", R.drawable.maghrib , R.drawable.maghrib_azan," كُنَّا نُصَلِّي المَغْرِبَ مع النبيِّ صَلَّى اللهُ عليه وسلَّمَ، فَيَنْصَرِفُ أحَدُنَا وإنَّه لَيُبْصِرُ مَوَاقِعَ نَبْلِهِ "
        ,"الراوي : رافع بن خديج | المحدث : البخاري | المصدر : صحيح البخاري"),
    ISHA("صلاة العشاء", R.drawable.isha, R.drawable.isha_azan,"صَلَّى لَنَا رَسولُ اللَّهِ صَلَّى اللهُ عليه وسلَّمَ لَيْلَةً صَلَاةَ العِشَاءِ، وهي الَّتي يَدْعُو النَّاسُ العَتَمَةَ، ثُمَّ انْصَرَفَ فأقْبَلَ عَلَيْنَا، فَقَالَ: أرَأَيْتُمْ لَيْلَتَكُمْ هذِه، فإنَّ رَأْسَ مِئَةِ سَنَةٍ منها، لا يَبْقَى مِمَّنْ هو علَى ظَهْرِ الأرْضِ أحَدٌ"
        ,"الراوي : عبدالله بن عمر | المحدث : البخاري | المصدر : صحيح البخاري")
}