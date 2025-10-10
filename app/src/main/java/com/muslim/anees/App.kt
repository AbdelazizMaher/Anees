package com.muslim.anees

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.disk.DiskCache
import coil.request.CachePolicy
import com.muslim.anees.utils.extensions.scheduleMidnightAlarmReset
import com.muslim.anees.utils.prayer_helper.PrayerTimesHelper
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class App : Application(), ImageLoaderFactory {

    override fun onCreate() {
        super.onCreate()
        scheduleMidnightAlarmReset()
        PrayerTimesHelper.init(this)
    }

    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(this)
            .crossfade(true)
            .diskCachePolicy(CachePolicy.ENABLED)
            .memoryCachePolicy(CachePolicy.ENABLED)
            .diskCache {
                DiskCache.Builder()
                    .directory(cacheDir.resolve("image_cache"))
                    .maxSizeBytes(150L * 1024 * 1024)
                    .build()
            }
            .build()
    }
}