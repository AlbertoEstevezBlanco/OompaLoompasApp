package com.example.oompaloompasapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.oompaloompasapp.oompaloompa.presenter.OompaLoompasViewModel
import com.example.oompaloompasapp.oompaloompa.presenter.intent.OompaLoompasIntent
import com.example.oompaloompasapp.oompaloompa.presenter.ui.screen.ScreenRenderer
import com.example.oompaloompasapp.oompaloompa.presenter.ui.theme.OompaLoompasAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val oompaLoompasViewModel: OompaLoompasViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OompaLoompasAppTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    ScreenRenderer(
                        state = oompaLoompasViewModel.uiState.collectAsState().value,
                        onIntent = { oompaLoompasViewModel.launchIntent(it) }
                    )
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        oompaLoompasViewModel.launchIntent(OompaLoompasIntent.GetOompaLoompas)
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    OompaLoompasAppTheme {
        Greeting("Android")
    }
}