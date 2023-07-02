package com.example.oompaloompasapp.oompaloompa.presenter.ui.screen


import androidx.compose.runtime.Composable
import com.example.oompaloompasapp.oompaloompa.presenter.DetailState
import com.example.oompaloompasapp.oompaloompa.presenter.ListState
import com.example.oompaloompasapp.oompaloompa.presenter.OompaLoompaState
import com.example.oompaloompasapp.oompaloompa.presenter.intent.OompaLoompasIntent

@Composable
fun ScreenRenderer(
    state: OompaLoompaState,
    onIntent: (OompaLoompasIntent) -> Unit
) = when(state) {
    is ListState -> OompaLoompaListScreen(state = state, onIntent = onIntent)
    is DetailState -> OompaLoompaDetailScreen(state = state, onIntent = onIntent)
    else -> {}
}