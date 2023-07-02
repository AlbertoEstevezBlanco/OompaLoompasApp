package com.example.oompaloompasapp.oompaloompa.application

import com.example.oompaloompasapp.oompaloompa.domain.OompaLoompa
import com.example.oompaloompasapp.oompaloompa.domain.OompaLoompaException
import com.example.oompaloompasapp.oompaloompa.domain.OompaLoompasRepository
import com.example.oompaloompasapp.oompaloompa.presenter.DetailState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class GetOompaLoompaDetails(
    private val oompaLoompasRepository: OompaLoompasRepository,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend operator fun invoke(oompaLoompa: OompaLoompa): Flow<DetailState> = flow {
        emit(DetailState.Loading)
        val detailsResult = withContext(ioDispatcher) {
            oompaLoompasRepository.getOompaLoompaDetials(oompaLoompa)
        }

        detailsResult
            .onSuccess { oompaLoompa ->
                emit(DetailState.Loaded(oompaLoompa))
            }
            .onFailure { error ->
                emit(DetailState.ErrorLoading(OompaLoompaException.GetDetailsException(error.message)))
            }
    }
}