package com.muslim.anees.utils.cashed_image

import android.content.Context
import coil.ImageLoader
import coil.disk.DiskCache
import coil.request.CachePolicy

object AppImageLoader {
    fun get(context: Context): ImageLoader {
        return ImageLoader.Builder(context)
            .crossfade(true)
            .diskCachePolicy(CachePolicy.ENABLED)
            .memoryCachePolicy(CachePolicy.ENABLED)
            .diskCache {
                DiskCache.Builder()
                    .directory(context.cacheDir.resolve("image_cache"))
                    .maxSizeBytes(150L * 1024 * 1024)
                    .build()
            }
            .build()
    }
}
