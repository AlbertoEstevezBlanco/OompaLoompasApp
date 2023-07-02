package com.example.oompaloompasapp.oompaloompa.presenter.ui.screen

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.oompaloompasapp.oompaloompa.domain.OompaLoompa
import com.example.oompaloompasapp.oompaloompa.presenter.DetailState
import com.example.oompaloompasapp.oompaloompa.presenter.intent.OompaLoompasIntent
import com.example.oompaloompasapp.oompaloompa.presenter.ui.screen.components.ErrorView
import com.example.oompaloompasapp.oompaloompa.presenter.ui.screen.components.LoadingAnimation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OompaLoompaDetailScreen(
    modifier: Modifier = Modifier,
    state: DetailState,
    onIntent: (OompaLoompasIntent) -> Unit
) {
    val openListView = { onIntent(OompaLoompasIntent.GetOompaLoompas) }
    Column(
        modifier = modifier
            .animateContentSize()
            .padding(16.dp)
    ) {
        TopAppBar(
            modifier = modifier.fillMaxWidth(),
            title = { Text("Detailed View") },
            actions = {
                IconButton(
                    onClick = openListView,
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Filter Menu"
                    )
                }
            }
        )
        Divider(modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp))
        when (state) {
            is DetailState.Loading -> LoadingAnimation(modifier = Modifier.fillMaxSize())
            is DetailState.Loaded -> OompaLoompaDetailedCard(modifier = Modifier.fillMaxSize(), oompaLoompa = state.oompaLoompa)
            is DetailState.ErrorLoading -> ErrorView(modifier = Modifier.fillMaxSize(), exception = state.error)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OompaLoompaDetailedCard(
    modifier: Modifier = Modifier,
    oompaLoompa: OompaLoompa
) {
    Box(modifier) {
        Card(Modifier.wrapContentSize()) {
            Row(Modifier.fillMaxWidth()) {
                oompaLoompa.image?.let {
                    AsyncImage(model = it, contentDescription = oompaLoompa.first_name)
                }
                Spacer(modifier = Modifier
                    .width(2.dp)
                    .background(MaterialTheme.colorScheme.primary))
                Column {
                    OompaLoompaProperty(label = "Full Name", value = "${oompaLoompa.first_name} ${oompaLoompa.last_name}")
                    OompaLoompaProperty(label = "Contact: ", value = oompaLoompa.email)
                    OompaLoompaProperty(label = "Profession", value = oompaLoompa.profession)
                    OompaLoompaProperty(label = "Heigth", value = oompaLoompa.height.toString())
                    OompaLoompaProperty(label = "Gender", value = oompaLoompa.gender)
                    OompaLoompaProperty(label = "Age", value = oompaLoompa.age.toString())
                }
            }
            Divider()
            LazyColumn(modifier = Modifier.padding(2.dp)) {
                items(14) {
                    OompaLoompaProperty(label = "About", value = oompaLoompa.description)
                    Divider()
                    OompaLoompaProperty(label = "Country", value = oompaLoompa.country)
                    Divider()
                    OompaLoompaProperty(label = "Color", value = oompaLoompa.favorite.color)
                    Divider()
                    OompaLoompaProperty(label = "Food", value = oompaLoompa.favorite.food)
                    Divider()
                    OompaLoompaProperty(label = "Song", value = oompaLoompa.favorite.song)
                    Divider()
                    OompaLoompaProperty(label = "Random", value = oompaLoompa.favorite.random_string)
                    Divider()
                    OompaLoompaProperty(label = "Quota", value = oompaLoompa.quota)
                }
            }
        }
    }
}

@Composable
fun OompaLoompaProperty(label: String, value: String, isLink: Boolean = false) {
    Row(modifier = Modifier.padding(16.dp)) {
        Text(
            text = label,
            modifier = Modifier.weight(0.3f),
            style = MaterialTheme.typography.labelMedium,
        )
        Text(
            text = value,
            modifier = Modifier.weight(0.7f),
            fontWeight = FontWeight.Bold,
            style = if (isLink) MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.primary)
            else MaterialTheme.typography.bodyLarge
        )
    }
}