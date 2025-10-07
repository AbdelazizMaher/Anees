package com.muslim.anees.data.remote.service

import com.muslim.anees.data.model.TafsierModel
import retrofit2.http.GET
import retrofit2.http.Path

interface TafsirService {
    @GET("/api/v1/translation/sura/arabic_moyassar/{sura_number}")
    suspend fun getAllTafsier(
        @Path("sura_number") suraNumber: String
    ): TafsierModel
}