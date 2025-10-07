package com.muslim.anees.data.remote

import com.muslim.anees.data.model.EditionResponse
import com.muslim.anees.data.model.TafsierModel

interface RemoteDataSource {
    suspend fun getAllSections(name: String): EditionResponse
    suspend fun getAllTafsier(name: String): TafsierModel

}