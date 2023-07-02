/*
 * Copyright (c) 2023 by Ancora. All rights reserved.
 * This code cannot be used, copied, modified and/or distributed without
 * the express permission of the authors.
 *
 * Created by alberto on 25/06/2023
 */

package com.example.oompaloompasapp.oompaloompa.application

import com.example.oompaloompasapp.oompaloompa.domain.OompaLoompaException
import com.example.oompaloompasapp.oompaloompa.domain.OompaLoompasRepository
import com.example.oompaloompasapp.oompaloompa.presenter.ListState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class GetOompaLoompas(
    private val oompaLoompasRepository: OompaLoompasRepository,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend operator fun invoke(): Flow<ListState> = flow {
        emit(ListState.Loading)
        val oompaLoompasResult = withContext(ioDispatcher) {
            oompaLoompasRepository.getOompaLoompas()
        }

        oompaLoompasResult
            .onSuccess { oompaLoompasList ->
                emit(ListState.Loaded(oompaLoompasList))
            }
            .onFailure { error ->
                emit(ListState.ErrorLoading(OompaLoompaException.GetAllException(error.message)))
            }
    }
}