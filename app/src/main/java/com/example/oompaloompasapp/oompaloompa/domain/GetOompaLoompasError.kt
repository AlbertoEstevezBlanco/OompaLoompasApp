package com.example.oompaloompasapp.oompaloompa.domain

sealed class OompaLoompaException(open val error: String?): Exception(error) {
    class GetAllException(override val error: String?): OompaLoompaException(error)
    class GetDetailsException(override val error: String?): OompaLoompaException(error)
}
