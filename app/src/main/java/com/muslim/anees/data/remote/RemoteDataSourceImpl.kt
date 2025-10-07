package com.muslim.anees.data.remote

import com.muslim.anees.data.model.EditionResponse
import com.muslim.anees.data.model.TafsierModel
import com.muslim.anees.data.remote.service.HadithApiService
import com.muslim.anees.data.remote.service.TafsirService
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val hadithApiService: HadithApiService,
    private val tafsirService: TafsirService
): RemoteDataSource {
    override suspend fun getAllSections(name: String): EditionResponse {
        return hadithApiService.getSections(name)
    }

    override suspend fun getAllTafsier(name: String): TafsierModel {
        return tafsirService.getAllTafsier(name)

    }


}