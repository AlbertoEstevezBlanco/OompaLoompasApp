package com.example.oompaloompasapp.oompaloompa.presenter.intent

import com.example.oompaloompasapp.oompaloompa.application.GetOompaLoompaDetails
import com.example.oompaloompasapp.oompaloompa.application.GetOompaLoompas
import com.example.oompaloompasapp.oompaloompa.presenter.OompaLoompaState
import kotlinx.coroutines.flow.Flow

class OompaLoompasIntentHandler(
    private val getOompaLoompas: GetOompaLoompas,
    private val getOompaLoompaDetails: GetOompaLoompaDetails
) {
    suspend fun handle(intent: OompaLoompasIntent): Flow<OompaLoompaState> =
        when(intent) {
            is OompaLoompasIntent.GetOompaLoompas -> getOompaLoompas()
            is OompaLoompasIntent.GetOompaLoompaDetails -> getOompaLoompaDetails(intent.oompaLoompa)
        }
}