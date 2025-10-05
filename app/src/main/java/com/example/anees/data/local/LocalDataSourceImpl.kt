package com.example.anees.data.local

import com.abdok.atmosphere.data.local.sharedPreference.ISharedPreferences
import com.example.anees.data.local.database.dao.AneesDao
import com.example.anees.data.local.database.dao.MahafogatDao
import com.example.anees.data.local.database.dao.TafsirDao
import com.example.anees.data.model.AzkarEntity
import com.example.anees.data.model.HadithEntity
import com.example.anees.data.model.Sebiha
import com.example.anees.data.model.TafsierModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val sharedPreferences: ISharedPreferences,
    private val dao: AneesDao,
    private val tafsirDao: TafsirDao,
    private val mahafogatDao: MahafogatDao
) : LocalDataSource {

    // --- SharedPreferences ---
    override fun saveData(key: String, value: Any) {
        sharedPreferences.saveData(key, value)
    }

    override fun <T> fetchData(key: String, defaultValue: T): T {
        return sharedPreferences.fetchData(key, defaultValue)
    }

    // --- Sebiha ---
    override suspend fun insertSebiha(sebiha: Sebiha) {
        dao.insertSebha(sebiha)
    }

    override fun getSebiha(): Flow<Sebiha> {
        return dao.getAllSebha()
    }

    // --- Tafsir ---
    override fun getTafsir(id: Int): Flow<TafsierModel?> {
        return tafsirDao.getAllTafsir(id)
    }

    override suspend fun insertTafsir(tafsir: TafsierModel) {
        tafsirDao.insertTafsir(tafsir)
    }

    // --- Azkar ---
    override suspend fun insertAzkar(azkar: AzkarEntity) {
        mahafogatDao.addAzkar(azkar)
    }

    override suspend fun deleteAzkar(azkar: AzkarEntity) {
        mahafogatDao.deleteAzkar(azkar.category)
    }

    override fun getSavedAzkarFlow(): Flow<List<AzkarEntity>> {
        return mahafogatDao.getAllSavedAzkarFlow()
    }

    override suspend fun isAzkarSaved(category: String): Boolean {
        return mahafogatDao.isAzkarSaved(category)
    }

    // --- Hadith ---
    override suspend fun insertHadith(hadith: HadithEntity) {
        mahafogatDao.addHadith(hadith)
    }

    override suspend fun deleteHadith(hadith: HadithEntity) {
        mahafogatDao.deleteHadith(hadith.title)
    }

    override fun getSavedHadithFlow(): Flow<List<HadithEntity>> {
        return mahafogatDao.getAllSavedHadithFlow()
    }

    override suspend fun isHadithSaved(title: String): Boolean {
        return mahafogatDao.isHadithSaved(title)
    }
}