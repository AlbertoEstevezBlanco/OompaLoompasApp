package com.example.oompaloompasapp.oompaloompa.presenter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.oompaloompasapp.oompaloompa.presenter.intent.OompaLoompasIntent
import com.example.oompaloompasapp.oompaloompa.presenter.intent.OompaLoompasIntentHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OompaLoompasViewModel @Inject constructor(
    private val oompaLoompasIntentHandler: OompaLoompasIntentHandler,
): ViewModel() {

    private val intentReceiver: MutableSharedFlow<OompaLoompasIntent> = MutableSharedFlow()
    private val intentFlow = intentReceiver.asSharedFlow()

    private val mutableUiStateFlow: MutableStateFlow<OompaLoompaState> = MutableStateFlow(
        ListState.Loading
    )
    val uiState = mutableUiStateFlow.asStateFlow()

    init {
        collectIntents()
    }

    fun launchIntent(intent: OompaLoompasIntent) = viewModelScope.launch {
        intentReceiver.emit(intent)
    }

    private fun collectIntents() = viewModelScope.launch {
        intentFlow.collect { handleIntent(it) }
    }

    private suspend fun handleIntent(intent: OompaLoompasIntent) =
        oompaLoompasIntentHandler.handle(intent).collect {
            mutableUiStateFlow.emit(it)
        }
}