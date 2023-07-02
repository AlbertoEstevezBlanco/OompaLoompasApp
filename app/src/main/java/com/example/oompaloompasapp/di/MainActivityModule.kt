package com.example.oompaloompasapp.di

import com.example.oompaloompasapp.oompaloompa.application.GetOompaLoompaDetails
import com.example.oompaloompasapp.oompaloompa.application.GetOompaLoompas
import com.example.oompaloompasapp.oompaloompa.infrastructure.NetworkOompaLoompasRepository
import com.example.oompaloompasapp.oompaloompa.presenter.intent.OompaLoompasIntentHandler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object MainActivityModule {

    @Provides
    fun provideOompaLoompasIntentHandler(): OompaLoompasIntentHandler {
        val repository = NetworkOompaLoompasRepository()
        return OompaLoompasIntentHandler(
            getOompaLoompas = GetOompaLoompas(oompaLoompasRepository = repository),
            getOompaLoompaDetails = GetOompaLoompaDetails(oompaLoompasRepository = repository)
        )
    }

}