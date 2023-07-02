package com.example.oompaloompasapp.oompaloompa.infrastructure

import com.example.oompaloompasapp.oompaloompa.domain.OompaLoompa
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface OompaLoompasService {

    @GET("oompa-loompas")
    suspend fun getOompaLoompas(@Query("page") page: Int): Response<GetOompaLoompasResponse>

    @GET("oompa-loompas/{id}")
    suspend fun getOompaLoompaDetails(@Path("id") id: String): Response<OompaLoompa>

}

data class GetOompaLoompasResponse(
    val current: Int,
    val total: Int,
    val results: List<OompaLoompa>
)