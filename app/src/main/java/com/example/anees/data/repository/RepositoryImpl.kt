package com.example.anees.data.repository

import com.example.anees.data.local.LocalDataSource
import com.example.anees.data.model.AzkarEntity
import com.example.anees.data.model.EditionResponse
import com.example.anees.data.model.HadithEntity
import com.example.anees.data.model.Sebiha
import com.example.anees.data.model.TafsierModel
import com.example.anees.data.remote.RemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : Repository {

    // --- SharedPreferences ---
    override fun saveData(key: String, value: Any) {
        localDataSource.saveData(key, value)
    }

    override fun <T> fetchData(key: String, defaultValue: T): T {
        return localDataSource.fetchData(key,defaultValue)
    }

    // --- Sebiha ---
    override suspend fun addSebiha(sebiha: Sebiha) {
        localDataSource.insertSebiha(sebiha)
    }

    override fun getSebiha(): Flow<Sebiha> {
        return localDataSource.getSebiha()
    }

    override suspend fun getAllSections(name: String): Flow<EditionResponse> {
        return flowOf(remoteDataSource.getAllSections(name))
    }

    // --- Tafsier ---
    override suspend fun getAllTafsier(name: String): Flow<TafsierModel> {
        return flowOf(remoteDataSource.getAllTafsier(name))
    }

    override suspend fun addTafsir(tafsir: TafsierModel) {
        localDataSource.insertTafsir(tafsir)
    }

    override fun getTafsir(id: Int): Flow<TafsierModel?> {
        return localDataSource.getTafsir(id)
    }

    // --- Azkar ---
    override suspend fun insertAzkar(azkar: AzkarEntity) {
        return localDataSource.insertAzkar(azkar)
    }

    override suspend fun deleteAzkar(azkar: AzkarEntity) {
        return localDataSource.deleteAzkar(azkar)
    }

    override suspend fun isAzkarSaved(category: String): Boolean {
        return localDataSource.isAzkarSaved(category)
    }

    override fun getSavedAzkarFlow(): Flow<List<AzkarEntity>> {
        return localDataSource.getSavedAzkarFlow()
    }

    override suspend fun toggleAzkar(category: String) {
        val exists = localDataSource.isAzkarSaved(category)
        if (exists) {
            localDataSource.deleteAzkar(AzkarEntity(category = category))
        } else {
            localDataSource.insertAzkar(AzkarEntity(category = category))
        }
    }

    // --- Hadith ---
    override suspend fun insertHadith(hadith: HadithEntity) {
        return localDataSource.insertHadith(hadith)
    }

    override suspend fun deleteHadith(hadith: HadithEntity) {
        return localDataSource.deleteHadith(hadith)
    }

    override suspend fun isHadithSaved(title: String): Boolean {
        return localDataSource.isHadithSaved(title)
    }

    override fun getSavedHadithFlow(): Flow<List<HadithEntity>> {
        return localDataSource.getSavedHadithFlow()
    }

    override suspend fun toggleHadith(title: String) {
        val exists = localDataSource.isHadithSaved(title)
        if (exists) {
            localDataSource.deleteHadith(HadithEntity(title = title))
        } else {
            localDataSource.insertHadith(HadithEntity(title = title))
        }
    }

}