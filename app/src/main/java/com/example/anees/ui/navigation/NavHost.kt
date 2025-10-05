package com.example.anees.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.anees.data.local.sharedpreference.SharedPreferencesImpl
import com.example.anees.data.model.RecitationModel
import com.example.anees.enums.AuthorEdition
import com.example.anees.enums.PrayEnum
import com.example.anees.enums.QuranSurah
import com.example.anees.ui.navigation.ScreenRoute.AzanPlayerScreen
import com.example.anees.ui.navigation.ScreenRoute.AzanSettingsPlayerScreen
import com.example.anees.ui.navigation.ScreenRoute.AzkarDetailsScreen
import com.example.anees.ui.navigation.ScreenRoute.FajrPlayerScreen
import com.example.anees.ui.screens.azan.AzanScreen
import com.example.anees.ui.screens.azkar.screens.AdhkarDetailsScreen
import com.example.anees.ui.screens.azkar.screens.AdhkarScreen
import com.example.anees.ui.screens.downloaded_audio.DownloadedAudioScreen
import com.example.anees.ui.screens.eLMahfogat.ElMahfogatScreen
import com.example.anees.ui.screens.hadith.HadithAuthorsScreen
import com.example.anees.ui.screens.hadith.HadithScreen
import com.example.anees.ui.screens.hadith.HadithSectionsScreen
import com.example.anees.ui.screens.hisn_almuslim.HisnAlMuslimScreen
import com.example.anees.ui.screens.home.HomeScreenWithDrawer
import com.example.anees.ui.screens.how_to_pray_screen.HowToPrayScreen
import com.example.anees.ui.screens.names_of_allah.NamesOfAllahScreen
import com.example.anees.ui.screens.prayer.PrayerScreen
import com.example.anees.ui.screens.qibla.QiblaScreen
import com.example.anees.ui.screens.quran_pdf.juz_index.JuzIndexScreen
import com.example.anees.ui.screens.quran_pdf.khatm.KhatmQuranDuaScreen
import com.example.anees.ui.screens.quran_pdf.quran.QuranPDFViewerScreen
import com.example.anees.ui.screens.quran_pdf.quran_index.QuranIndexScreen
import com.example.anees.ui.screens.radio.RadioScreen
import com.example.anees.ui.screens.reciters.view.screens.QuranPlayerScreen
import com.example.anees.ui.screens.reciters.view.screens.RecitersScreen
import com.example.anees.ui.screens.reciters.view.screens.SuraMp3Screen
import com.example.anees.ui.screens.sebha.SebihaScreen
import com.example.anees.ui.screens.settings.SettingsScreen
import com.example.anees.ui.screens.splash.SplashScreen
import com.example.anees.ui.screens.tafsir.screens.TafsirDetailsScreen
import com.example.anees.ui.screens.tafsir.screens.TafsirScreen
import com.example.anees.utils.Constants
import com.example.anees.utils.extensions.convertNumbersToArabic
import com.example.anees.utils.extensions.toArabicTime
import com.example.anees.utils.prayer_helper.PrayerTimesHelper
import com.google.gson.Gson


@Composable
fun SetUpNavHost(
    navController: NavHostController,
    location: MutableState<Coordinates>,
    readyToShowPermissions: MutableState<Boolean>
) {
    NavHost(
        navController = navController,
        startDestination = ScreenRoute.SplashScreen
    ) {
        composable<ScreenRoute.SplashScreen> {
            SplashScreen(navToHome = {
                navController.popBackStack()
                navController.navigate(ScreenRoute.HomeScreen)
                readyToShowPermissions.value = true
            })
        }

        composable<ScreenRoute.HomeScreen> {
            HomeScreenWithDrawer(
                location = location,
                navToSebiha = {
                    navController.navigate(ScreenRoute.Sebiha)
                },
                navToQibla = {
                    navController.navigate(ScreenRoute.QiblaScreen)
                },
                navToQuran = {
                    navController.navigate(ScreenRoute.CompleteQuranScreen)
                },
                navToAzkar = {
                    navController.navigate(ScreenRoute.AdhkarScreen)
                },
                navToHadith = {
                    navController.navigate(ScreenRoute.HadithAuthorsScreen)
                },
                navToPrayer = {
                    navController.navigate(ScreenRoute.PrayerTimesScreen)
                },
                navToRadio = {
                    navController.navigate(ScreenRoute.RadioScreen)
                },
                navToTafsir = {
                    navController.navigate(ScreenRoute.TafsirScreen)
                },
                navToReciters = {
                    navController.navigate(ScreenRoute.RecitersScreen)
                },
                navToNamesOfAllah = {
                    navController.navigate(ScreenRoute.NamesOfAllahScreen)
                },
                navToHisnAlMuslim = {
                    navController.navigate(ScreenRoute.HisnAlMuslimScreen)
                },
                navToSettings = {
                    navController.navigate(ScreenRoute.SettingsScreen)
                },
                navToPrayScreen = {
                    navController.navigate(ScreenRoute.PrayScreen)
                },
                navToElMahfogat = {
                    navController.navigate(ScreenRoute.ElMahfogatScreen)
                }
            )
        }
        composable<ScreenRoute.Sebiha> {
            SebihaScreen {
                navController.popBackStack()
                navController.navigate(ScreenRoute.HomeScreen)
            }
        }
        composable<ScreenRoute.DownloadQuranScreen> {
            DownloadedAudioScreen(
                onBackClick = {
                    navController.popBackStack()
                    navController.navigate(ScreenRoute.HomeScreen)
                },
                onSuraClicked = {
                    navController.navigate(ScreenRoute.QuranPlayerScreen(
                        recitationModel = null,
                        recitationName = null,
                        index = it,
                        isOnline = false
                    ))
                }
            )
        }
        composable<ScreenRoute.QiblaScreen> {
            QiblaScreen{
                navController.popBackStack()
            }
        }

        composable<ScreenRoute.CompleteQuranScreen> {
            val sharedPref = SharedPreferencesImpl(navController.context)
            val initPage = sharedPref.fetchData(Constants.CURRENT_PAGE_INDEX, 658)
            QuranPDFViewerScreen(
                initPage = initPage,
                onIndexButtonClick = {
                    navController.navigate(ScreenRoute.QuranIndexScreen)
                },
                onKhatmButtonClick = {
                    navController.navigate(ScreenRoute.KhatmQuranDuaScreen)
                },
                onJuzButtonClick = {
                    navController.navigate(ScreenRoute.JuzIndexScreen)
                }
            )
        }

        composable<ScreenRoute.AdhkarScreen> {
            AdhkarScreen(
                navToHome = {
                    navController.navigateUp()
                },
                navToDetails = { cat ->
                    //  navController.navigateUp()
                    navController.navigate(AzkarDetailsScreen(cat))
                }
            )
        }
        composable<AzkarDetailsScreen> {
            val asd = it.arguments?.getString("category") ?: ""
            AdhkarDetailsScreen(asd) {
                navController.popBackStack()
                // navController.navigate(ScreenRoute.AdhkarScreen)
            }
        }
        composable<ScreenRoute.HadithScreen> {
            val author = Gson().fromJson(
                it.arguments?.getString("author"),
                AuthorEdition::class.java
            )
            val id = it.arguments?.getString("number") ?: ""
            HadithScreen(author, id, {
                navController.navigateUp()
            })
        }
        composable<ScreenRoute.HadithAuthorsScreen> {
            HadithAuthorsScreen(
                onBackClick = {
                    navController.navigateUp()
                }, navToHadithsSections = {
                    navController.navigate(ScreenRoute.HadithSectionsScreen(it))
                }
            )
        }
        composable<ScreenRoute.HadithSectionsScreen> {
            val author = Gson().fromJson(
                it.arguments?.getString("author"),
                AuthorEdition::class.java
            )
            HadithSectionsScreen(
                author, { auth, id ->
                    navController.navigate(ScreenRoute.HadithScreen(auth, id))
                },
                onBackClick = {
                    navController.navigateUp()
                }
            )
        }
        composable<ScreenRoute.QuranIndexScreen> {
            QuranIndexScreen() {
                navController.popBackStack()
                navController.popBackStack()
                navController.navigate(ScreenRoute.CompleteQuranScreen)
            }
        }


        composable<ScreenRoute.JuzIndexScreen> {
            JuzIndexScreen {
                navController.popBackStack()
                navController.popBackStack()
                navController.navigate(ScreenRoute.CompleteQuranScreen)
            }
        }

        composable<ScreenRoute.KhatmQuranDuaScreen> {
            KhatmQuranDuaScreen()
        }

        composable<ScreenRoute.PrayScreen> {
            HowToPrayScreen {
                navController.navigateUp()
            }
        }
        composable<ScreenRoute.PrayerTimesScreen> {
            PrayerScreen(
                location = location,
                onPreviewClick = {
                    navController.navigate(AzanPlayerScreen)
                }
            ) {
                navController.navigateUp()
            }
        }

        composable<ScreenRoute.ElMahfogatScreen> {
            ElMahfogatScreen(
                navToHome = {
                    navController.navigateUp()
                },
                navToAzkarDetails = { azkarCategoty ->
                    navController.navigate(AzkarDetailsScreen(azkarCategoty))
                },
                navToReciter = {
                    navController.navigate(ScreenRoute.QuranPlayerScreen(
                        recitationModel = null,
                        recitationName = null,
                        index = it,
                        isOnline = false
                    ))
                }
            )
        }

        composable<ScreenRoute.RadioScreen> {
            RadioScreen{
                navController.navigateUp()
            }
        }

        composable<ScreenRoute.TafsirScreen> {
            TafsirScreen (navToDetails = {
                navController.navigateUp()
                navController.navigate(ScreenRoute.TafsirDetailsScreen(it))
            },navToHome = {
                navController.navigateUp()
            }
            )
        }

        composable<ScreenRoute.NamesOfAllahScreen> {
            NamesOfAllahScreen {
                navController.navigateUp()
            }
        }

        composable<ScreenRoute.TafsirDetailsScreen> {
            val surah = Gson().fromJson(
                it.arguments?.getString("surah"),
                QuranSurah::class.java
            )
            TafsirDetailsScreen(surah){
                navController.navigateUp()
                navController.navigate(ScreenRoute.TafsirScreen)
            }
        }
        composable<ScreenRoute.RecitersScreen> {
            RecitersScreen(onBackClick = {
                navController.navigateUp()
            }) { recitationModel , recitationName ->
                navController.navigate(ScreenRoute.SuraMp3Screen(Gson().toJson(recitationModel) , recitationName))
            }
        }

        composable<ScreenRoute.SuraMp3Screen> {
            val recitationModel = Gson().fromJson(
                it.arguments?.getString("recitationModel"),
                RecitationModel::class.java
            )

            val recitationName = it.arguments?.getString("recitationName") ?: ""

            SuraMp3Screen(recitationModel , recitationName, onBackClick = {
                navController.navigateUp()
            }) { recitationModel , recitationName, index ->
                navController.navigate(ScreenRoute.QuranPlayerScreen(recitationModel , recitationName, index))
            }
        }

        composable<ScreenRoute.QuranPlayerScreen> {
            val recitationModel = Gson().fromJson(
                it.arguments?.getString("recitationModel"),
                RecitationModel::class.java
            )
            val isOnline = it.arguments?.getBoolean("isOnline") ?: true
            val recitationName = it.arguments?.getString("recitationName") ?: ""

            val index = it.arguments?.getInt("index") ?: 0
            QuranPlayerScreen(
                initialSuraIndex = index,
                isOnline = isOnline,
                recitationModel = recitationModel,
                recitationName = recitationName,
            ) {
                navController.navigateUp()
            }
        }

        composable<ScreenRoute.HisnAlMuslimScreen> {
            HisnAlMuslimScreen() {
                navController.navigateUp()
            }
        }

        composable<AzanPlayerScreen> {
            val (prayEnum , time) = PrayerTimesHelper.getNextPrayer() ?: (PrayEnum.FAJR to 0L)
            AzanScreen(
                prayEnum = prayEnum,
                prayerTime = time.toArabicTime().convertNumbersToArabic(),
            ){
                navController.navigateUp()
            }
        }

        composable<AzanSettingsPlayerScreen> {
            var (prayEnum , time) = PrayerTimesHelper.getNextPrayer() ?: (PrayEnum.ZUHR to 0L)
            if (prayEnum == PrayEnum.FAJR){
                prayEnum =  PrayerTimesHelper.getAllPrayers()[1].first
                time = PrayerTimesHelper.getAllPrayers()[1].second
            }
            AzanScreen(
                prayEnum = prayEnum,
                prayerTime = time.toArabicTime().convertNumbersToArabic(),
            ){
                navController.navigateUp()
            }
        }

        composable<FajrPlayerScreen> {
            val (prayEnum , time) =  PrayerTimesHelper.getAllPrayers()[0]
            AzanScreen(
                prayEnum = prayEnum,
                prayerTime = time.toArabicTime().convertNumbersToArabic(),
            ){
                navController.navigateUp()
            }
        }

        composable<ScreenRoute.SettingsScreen> {
            SettingsScreen(onBackClick = {
                navController.navigateUp()
            },
                onAzanViewClick = {
                    navController.navigate(AzanSettingsPlayerScreen)
                },
                onFajarClick = {
                    navController.navigate(FajrPlayerScreen)
                }
            )
        }
    }
}