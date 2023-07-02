package com.oompaloompasapp.di

import com.example.oompaloompasapp.di.MainActivityModule
import com.example.oompaloompasapp.oompaloompa.application.GetOompaLoompaDetails
import com.example.oompaloompasapp.oompaloompa.application.GetOompaLoompas
import com.example.oompaloompasapp.oompaloompa.domain.OompaLoompasRepository
import com.example.oompaloompasapp.oompaloompa.presenter.intent.OompaLoompasIntentHandler
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import org.mockito.Mockito
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [MainActivityModule::class]
)
class FakeMainActivityModule {

    @Singleton
    @Provides
    fun provideOompaLoompasFakeRepository(): OompaLoompasRepository =
        Mockito.mock(OompaLoompasRepository::class.java)

    @Singleton
    @Provides
    fun provideOompaLoompasIntentHandler(
        repository: OompaLoompasRepository
    ): OompaLoompasIntentHandler {
        return OompaLoompasIntentHandler(
            getOompaLoompas = GetOompaLoompas(oompaLoompasRepository = repository),
            getOompaLoompaDetails = GetOompaLoompaDetails(oompaLoompasRepository = repository)
        )
    }

}