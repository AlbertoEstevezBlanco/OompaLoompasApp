package com.example.oompaloompasapp.oompaloompa.presenter

import com.example.oompaloompasapp.oompaloompa.domain.OompaLoompa
import com.example.oompaloompasapp.oompaloompa.domain.OompaLoompaException

interface OompaLoompaState

sealed interface ListState: OompaLoompaState {
    object Loading: ListState
    class Loaded(val oompaLoompas: List<OompaLoompa>): ListState
    class ErrorLoading(val error: OompaLoompaException): ListState
}

sealed interface DetailState: OompaLoompaState {
    object Loading: DetailState
    class Loaded(val oompaLoompa: OompaLoompa): DetailState
    class ErrorLoading(val error: OompaLoompaException): DetailState
}
