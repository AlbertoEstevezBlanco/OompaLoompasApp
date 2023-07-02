package com.example.oompaloompasapp.oompaloompa.presenter.ui.screen.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.oompaloompasapp.oompaloompa.domain.OompaLoompa

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OompaLoompasSummaryScreenHeader(
    modifier: Modifier = Modifier,
    applyFilter: ((OompaLoompa) -> Boolean) -> Unit,
    refreshOompaLoompas: () -> Unit
) {
    var showFilterMenu by remember { mutableStateOf(false) }
    TopAppBar(
        modifier = modifier.fillMaxWidth(),
        title = { Text("Oompa Loompas") },
        actions = {
            IconButton(
                onClick = { showFilterMenu = !showFilterMenu },
            ) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Filter Menu"
                )
            }
        }
    )
    AnimatedVisibility(
        visible = showFilterMenu,
        enter = fadeIn() + slideInVertically(),
        exit = fadeOut() + slideOutVertically()
    ) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            FilterMenu(
                modifier = Modifier
                    .weight(3f)
                    .padding(vertical = 16.dp),
                applyFilter = applyFilter
            )
            IconButton(modifier = Modifier.weight(1f), onClick = refreshOompaLoompas) {
                Icon(imageVector = Icons.Filled.Refresh, contentDescription = "Refresh oompa loompas")
            }
        }
    }
}