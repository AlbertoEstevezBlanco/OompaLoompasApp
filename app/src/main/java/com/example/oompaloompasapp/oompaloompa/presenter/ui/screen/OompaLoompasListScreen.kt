package com.example.oompaloompasapp.oompaloompa.presenter.ui.screen

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.oompaloompasapp.R
import com.example.oompaloompasapp.oompaloompa.domain.OompaLoompa
import com.example.oompaloompasapp.oompaloompa.presenter.ListState
import com.example.oompaloompasapp.oompaloompa.presenter.intent.OompaLoompasIntent
import com.example.oompaloompasapp.oompaloompa.presenter.ui.screen.components.ErrorView
import com.example.oompaloompasapp.oompaloompa.presenter.ui.screen.components.LoadingAnimation
import com.example.oompaloompasapp.oompaloompa.presenter.ui.screen.components.OompaLoompasSummaryScreenHeader

@Composable
fun OompaLoompaListScreen(
    modifier: Modifier = Modifier,
    state: ListState,
    onIntent: (OompaLoompasIntent) -> Unit
) {
    val oompaLoompas = remember(state) {
        if (state is ListState.Loaded) state.oompaLoompas else emptyList()
    }
    var filteredOompaLoompas by remember(oompaLoompas) { mutableStateOf(oompaLoompas) }

    val filterOompaLoompas: ((OompaLoompa) -> Boolean) -> Unit =
        { filter -> filteredOompaLoompas = oompaLoompas.filter { filter(it) } }

    val openDetailedView: (OompaLoompa) -> Unit =
        { onIntent(OompaLoompasIntent.GetOompaLoompaDetails(it)) }

    val refreshOompaLoompas: () -> Unit =
        { onIntent(OompaLoompasIntent.GetOompaLoompas) }

    Column(
        modifier = modifier
            .animateContentSize()
            .padding(16.dp)
    ) {
        OompaLoompasSummaryScreenHeader(
            applyFilter = filterOompaLoompas,
            refreshOompaLoompas = refreshOompaLoompas
        )
        Divider(modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp))
        when (state) {
            is ListState.Loading -> LoadingAnimation(modifier = Modifier.fillMaxSize())
            is ListState.Loaded -> {
                if (filteredOompaLoompas.isEmpty()) EmptyOompaLoompaList(modifier = Modifier.fillMaxSize())
                else OompaLoompasGrid(oompaLoompas = filteredOompaLoompas, openDetailedView = openDetailedView)
            }
            is ListState.ErrorLoading -> ErrorView(modifier = Modifier.fillMaxSize(), exception = state.error)
        }
    }
}

@Composable
fun EmptyOompaLoompaList(modifier: Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            modifier = Modifier.size(48.dp),
            imageVector = Icons.Default.Warning,
            contentDescription = "No oompa loompas icon"
        )
        Text(
            text = stringResource(id = R.string.no_oompa_loompas_message),
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )
    }
}
@Composable
fun OompaLoompasGrid(
    modifier: Modifier = Modifier,
    oompaLoompas: List<OompaLoompa>,
    openDetailedView: (OompaLoompa) -> Unit,
) {
    LazyVerticalGrid(
        modifier = modifier.testTag("OompaLoompas"),
        columns = GridCells.Adaptive(128.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(oompaLoompas) {
            OompaLoompaCard(oompaLoompa = it, openDetailedView = openDetailedView)
        }
    }
}

@Composable
fun OompaLoompaCard(
    modifier: Modifier = Modifier,
    oompaLoompa: OompaLoompa,
    openDetailedView: (OompaLoompa) -> Unit
) {
    Card(
        modifier = modifier
            .clickable { openDetailedView(oompaLoompa) }
            .wrapContentSize(),
        border = BorderStroke(width = 2.dp, color = MaterialTheme.colorScheme.primary)
    ) {
        oompaLoompa.image?.let {
            AsyncImage(model = it, contentDescription = oompaLoompa.first_name)
        }
        Text(
            modifier = Modifier
                .fillMaxSize()
                .padding(5.dp),
            text = oompaLoompa.first_name,
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
        Text(
            modifier = Modifier
                .fillMaxSize()
                .padding(5.dp),
            text = "${oompaLoompa.profession} · ${oompaLoompa.gender}",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            fontWeight = FontWeight.Bold
        )
    }
}