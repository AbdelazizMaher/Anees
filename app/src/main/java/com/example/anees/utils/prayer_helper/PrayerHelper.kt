package com.example.anees.utils.prayer_helper


import android.annotation.SuppressLint
import android.content.Context
import android.media.RingtoneManager
import android.net.Uri
import androidx.core.net.toUri
import com.batoulapps.adhan.CalculationMethod
import com.batoulapps.adhan.Coordinates
import com.batoulapps.adhan.PrayerTimes
import com.batoulapps.adhan.data.DateComponents
import com.example.anees.R
import com.example.anees.data.local.sharedpreference.SharedPreferencesImpl
import com.example.anees.enums.PrayEnum
import java.util.Calendar
import java.util.Date


@SuppressLint("StaticFieldLeak")
object PrayerTimesHelper {

    private lateinit var context: Context

    fun init(context: Context) {
        this.context = context
    }

    fun getCoordinates(): Coordinates {
        val latitude = SharedPreferencesImpl(context).fetchData("latitude", 30.033333)
        val longitude = SharedPreferencesImpl(context).fetchData("longitude", 31.233334)
        return Coordinates(latitude, longitude)
    }

    fun getNextPrayer(): Pair<PrayEnum, Long>? {
        val dateComponents = DateComponents.from(Date())

        val params = CalculationMethod.EGYPTIAN.parameters
        //params.madhab = Madhab.SHAFI

        val prayerTimes = PrayerTimes(getCoordinates(), dateComponents, params)

        val now = System.currentTimeMillis()

        val prayers = listOf(
            PrayEnum.FAJR to prayerTimes.fajr,
            PrayEnum.ZUHR to prayerTimes.dhuhr,
            PrayEnum.ASR to prayerTimes.asr,
            PrayEnum.MAGHRIB to prayerTimes.maghrib,
            PrayEnum.ISHA to prayerTimes.isha
        )

        for ((prayerEnum, date) in prayers) {
            val timeMillis = date.time
            if (timeMillis > now) {
                return Pair(prayerEnum, timeMillis)
            }
        }

        val tomorrow = Calendar.getInstance().apply { add(Calendar.DATE, 1) }
        val tomorrowDate = DateComponents.from(tomorrow.time)
        val tomorrowPrayerTimes = PrayerTimes(getCoordinates(), tomorrowDate, params)

        val fajrMillis = tomorrowPrayerTimes.fajr.time

        return Pair(PrayEnum.FAJR, fajrMillis)
    }

    fun getAllPrayers(): List<Triple<PrayEnum, Long, Boolean>> {
        val dateComponents = DateComponents.from(Date())

        val params = CalculationMethod.EGYPTIAN.parameters
        //params.madhab = Madhab.SHAFI

        val prayerTimes = PrayerTimes(getCoordinates(), dateComponents, params)

        return listOf(

            Triple(PrayEnum.FAJR, prayerTimes.fajr.time,isPrayerTime(PrayEnum.FAJR)),
            Triple(PrayEnum.ZUHR, prayerTimes.dhuhr.time, isPrayerTime(PrayEnum.ZUHR)),
            Triple(PrayEnum.ASR, prayerTimes.asr.time, isPrayerTime(PrayEnum.ASR)),
            Triple(PrayEnum.MAGHRIB, prayerTimes.maghrib.time, isPrayerTime(PrayEnum.MAGHRIB)),
            Triple(PrayEnum.ISHA, prayerTimes.isha.time, isPrayerTime(PrayEnum.ISHA))
        )
    }

    fun getUpcomingPrayers(): List<Pair<PrayEnum, Long>> {
        val now = System.currentTimeMillis()

        val result = mutableListOf<Pair<PrayEnum, Long>>()

        fun getPrayersForDate(date: Date): List<Pair<PrayEnum, Long>> {
            val dateComponents = DateComponents.from(date)
            val params = CalculationMethod.EGYPTIAN.parameters
            val prayerTimes = PrayerTimes(getCoordinates(), dateComponents, params)
            return listOf(
                PrayEnum.FAJR to prayerTimes.fajr.time,
                PrayEnum.ZUHR to prayerTimes.dhuhr.time,
                PrayEnum.ASR to prayerTimes.asr.time,
                PrayEnum.MAGHRIB to prayerTimes.maghrib.time,
                PrayEnum.ISHA to prayerTimes.isha.time
            )
        }

        val todayPrayers = getPrayersForDate(Date())
        for ((prayer, time) in todayPrayers) {
            if (time > now) {
                result.add(prayer to time)
            }
        }

        if (result.isEmpty()) {
            val tomorrow = Calendar.getInstance().apply { add(Calendar.DATE, 1) }.time
            val tomorrowPrayers = getPrayersForDate(tomorrow)
            result.addAll(tomorrowPrayers)
        }

        return result
    }




    private fun isPrayerTime(prayerEnum: PrayEnum): Boolean {
        val(prayer,time)=getNextPrayer() ?: return false
        return  prayerEnum == prayer
    }

    fun isTodayFriday(): Boolean {
        val today = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
        return today == Calendar.FRIDAY
    }


}


