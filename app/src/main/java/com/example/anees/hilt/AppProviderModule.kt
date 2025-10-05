package com.example.anees.hilt


import android.content.Context
import com.example.anees.data.local.database.AneesDatabase
import com.example.anees.data.local.database.dao.AneesDao
import com.example.anees.data.local.database.dao.MahafogatDao
import com.example.anees.data.local.database.dao.TafsirDao
import com.example.anees.data.remote.service.HadithApiService
import com.example.anees.data.remote.service.TafsirService
import com.example.anees.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppProviderModule  {
    @Provides
     fun bindDatabase(@ApplicationContext context: Context): AneesDatabase {
        return AneesDatabase.getInstance(context)
    }

    @Provides
    fun bindDao(db: AneesDatabase): AneesDao {
        return db.getDao()
    }

    @Provides
    fun bindTafsirDao(db: AneesDatabase): TafsirDao {
        return db.getTafsirDao()
    }

    @Provides
    fun bindMahafogatDao(db: AneesDatabase): MahafogatDao{
        return db.getMahafogatDao()
    }

    @Provides
    @Singleton
    @Named("HadithApi")
    fun provideHadithRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(Constants.API_HADITH_)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    @Provides
    @Singleton
    fun provideHadithApiService(@Named("HadithApi") retrofit: Retrofit): HadithApiService =
        retrofit.create(HadithApiService::class.java)
    @Provides
    @Singleton
    @Named("TafsirApi")
    fun provideTafsir(): Retrofit =
        Retrofit.Builder()
            .baseUrl(Constants.API_Tafsir_)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    @Provides
    @Singleton
    fun provideTafsirApiService(@Named("TafsirApi") retrofit: Retrofit): TafsirService =
        retrofit.create(TafsirService::class.java)


}