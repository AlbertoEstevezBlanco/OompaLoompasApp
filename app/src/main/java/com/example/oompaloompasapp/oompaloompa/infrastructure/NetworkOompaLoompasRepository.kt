package com.example.oompaloompasapp.oompaloompa.infrastructure

import com.example.oompaloompasapp.oompaloompa.domain.OompaLoompa
import com.example.oompaloompasapp.oompaloompa.domain.OompaLoompaException
import com.example.oompaloompasapp.oompaloompa.domain.OompaLoompasRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkOompaLoompasRepository: OompaLoompasRepository {

    private val oompaLoompasService: OompaLoompasService = with(
        Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    ) {
        create(OompaLoompasService::class.java)
    }

    override suspend fun getOompaLoompas(): Result<List<OompaLoompa>> {
        val response = oompaLoompasService.getOompaLoompas(1)
        val oompaLoompasResponse = response.body()
        return if (response.isSuccessful && oompaLoompasResponse != null) {
            Result.success(oompaLoompasResponse.results)
        } else Result.failure(OompaLoompaException.GetAllException(response.message()))
    }

    override suspend fun getOompaLoompaDetials(oompaLoompa: OompaLoompa): Result<OompaLoompa> {
        val response = oompaLoompasService.getOompaLoompaDetails(oompaLoompa.id)
        val oompaLoompaDetails = response.body()
        return if (response.isSuccessful && oompaLoompaDetails != null) {
            Result.success(oompaLoompaDetails)
        } else Result.failure(OompaLoompaException.GetDetailsException(response.message()))
    }

    companion object {
        private val BASE_URL = "https://2q2woep105.execute-api.eu-west-1.amazonaws.com/napptilus/"
    }
}