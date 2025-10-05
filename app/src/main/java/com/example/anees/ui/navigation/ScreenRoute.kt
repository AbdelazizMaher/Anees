package com.example.anees.ui.navigation


import kotlinx.serialization.Serializable


sealed class ScreenRoute {
    @Serializable
    object SplashScreen : ScreenRoute()
    @Serializable
    object HomeScreen : ScreenRoute()
    @Serializable
    object Sebiha : ScreenRoute()
    @Serializable
    object QiblaScreen : ScreenRoute()
    @Serializable
    object CompleteQuranScreen : ScreenRoute()
    @Serializable
    object AdhkarScreen : ScreenRoute()
    @Serializable
    data class AzkarDetailsScreen(val category: String) : ScreenRoute()
    @Serializable
    data class HadithScreen(val author: String, val number: String) : ScreenRoute()
    @Serializable
    data object HadithAuthorsScreen : ScreenRoute()
    @Serializable
    data class HadithSectionsScreen(val author: String) : ScreenRoute()
    @Serializable
    object QuranIndexScreen : ScreenRoute()
    @Serializable
    object JuzIndexScreen : ScreenRoute()
    @Serializable
    object KhatmQuranDuaScreen : ScreenRoute()
    @Serializable
    object PrayerTimesScreen : ScreenRoute()
    @Serializable
    object RadioScreen : ScreenRoute()
    @Serializable
    object TafsirScreen : ScreenRoute()
    @Serializable
    data class TafsirDetailsScreen (val surah: String): ScreenRoute()
    @Serializable
    object RecitersScreen : ScreenRoute()
    @Serializable
    object NamesOfAllahScreen : ScreenRoute()
    @Serializable
    data class  SuraMp3Screen(val recitationModel: String , val recitationName : String) : ScreenRoute()
    @Serializable
    data class  QuranPlayerScreen(val recitationModel: String?, val recitationName: String?, val index: Int, val isOnline: Boolean = true) : ScreenRoute()
    @Serializable
    object HisnAlMuslimScreen : ScreenRoute()
    @Serializable
    object  AzanPlayerScreen : ScreenRoute()

    @Serializable
    object  AzanSettingsPlayerScreen : ScreenRoute()

    @Serializable
    object  FajrPlayerScreen : ScreenRoute()

    @Serializable
    object  SettingsScreen : ScreenRoute()

    //pray screens
    @Serializable
    object PrayScreen : ScreenRoute()

    @Serializable
    object ElMahfogatScreen : ScreenRoute()


    @Serializable
    object DownloadQuranScreen : ScreenRoute()
}