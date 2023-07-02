package com.example.oompaloompasapp.oompaloompa.presenter.intent

import com.example.oompaloompasapp.oompaloompa.domain.OompaLoompa

sealed class OompaLoompasIntent {
    object GetOompaLoompas: OompaLoompasIntent()
    class GetOompaLoompaDetails(val oompaLoompa: OompaLoompa): OompaLoompasIntent()
}