package com.example.oompaloompasapp.oompaloompa.presenter.ui.screen.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.oompaloompasapp.oompaloompa.domain.OompaLoompa

@Composable
fun FilterMenu(
    modifier: Modifier = Modifier,
    applyFilter: ((OompaLoompa) -> Boolean) -> Unit
) {
    val professionState: MutableState<String?> = remember { mutableStateOf(null) }
    val genderState: MutableState<String?> = remember { mutableStateOf(null) }

    val filters: (OompaLoompa) -> Boolean = {
        val professionFilter = if (!professionState.value.isNullOrEmpty()) it.profession == professionState.value else true
        val genderFilter = if (!genderState.value.isNullOrEmpty()) it.gender == genderState.value else true
        genderFilter && professionFilter
    }

    Row(
        modifier = modifier.fillMaxWidth()
    ) {
        ProfessionFilter(
            modifier = Modifier.weight(0.5f),
            applyFilterCallback = { applyFilter(filters) },
            professionState = professionState
        )
        GenderFilter(
            modifier = Modifier.weight(0.5f),
            applyFilterCallback = { applyFilter(filters) },
            genderState = genderState
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfessionFilter(
    modifier: Modifier = Modifier,
    applyFilterCallback: () -> Unit,
    professionState: MutableState<String?>,
) {
    Row(modifier = modifier) {
        OutlinedTextField(
            value = professionState.value ?: "",
            shape = RoundedCornerShape(10.dp),
            onValueChange = { professionState.value = it },
            label = { Text("Filter by profession") },
            singleLine = true,
            trailingIcon = {
                Row {
                    IconButton(onClick = applyFilterCallback) {
                        Icon(imageVector = Icons.Filled.Search, contentDescription = "Apply profession filter")
                    }
                    IconButton(
                        onClick = {
                            professionState.value = null
                            applyFilterCallback()
                        }
                    ) {
                        Icon(imageVector = Icons.Filled.Clear, contentDescription = "Clear profession filter")
                    }
                }
            }
        )

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenderFilter(
    modifier: Modifier = Modifier,
    applyFilterCallback: () -> Unit,
    genderState: MutableState<String?>
) {
    Row(modifier = modifier) {
        OutlinedTextField(
            value = genderState.value ?: "",
            shape = RoundedCornerShape(10.dp),
            onValueChange = { genderState.value = it },
            label = { Text("Filter by gender") },
            trailingIcon = {
                Row {
                    IconButton(onClick = applyFilterCallback ) {
                        Icon(imageVector = Icons.Filled.Search, contentDescription = "Apply gender filter")
                    }
                    IconButton(
                        onClick = {
                            genderState.value = null
                            applyFilterCallback()
                        }) {
                        Icon(imageVector = Icons.Filled.Clear, contentDescription = "Clear gender filter")
                    }
                }
            }
        )
    }

}