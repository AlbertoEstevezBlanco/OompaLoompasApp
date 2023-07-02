package com.example.oompaloompasapp.oompaloompa.domain

interface OompaLoompasRepository {
    suspend fun getOompaLoompas(): Result<List<OompaLoompa>>
    suspend fun getOompaLoompaDetials(oompaLoompa: OompaLoompa): Result<OompaLoompa>
}