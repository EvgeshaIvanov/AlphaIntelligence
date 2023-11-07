package com.presentation

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.kodein.rememberScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.presentation.contract.CoinsEvent
import com.presentation.contract.CoinsViewState

class CoinsScreen : Screen {
    @Composable
    override fun Content() {
        val coinsViewModel = rememberScreenModel<CoinsViewModel>()

        val state by coinsViewModel.uiState.collectAsState()

        val navigator = LocalNavigator.currentOrThrow

        LaunchedEffect(Unit) {
            coinsViewModel.setEvent(CoinsEvent.OnCreate)
        }

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Crossfade(targetState = state.progress, label = "News") {
                when (it) {
                    CoinsViewState.Progress.Loading -> Box(
                        modifier = Modifier
                            .background(Color.Blue)
                            .fillMaxSize()
                    )

                    CoinsViewState.Progress.Content -> {
                        LazyColumn {
                            items(items = state.coins) { coin ->
                                Column {
                                    Text(text = coin.id)
                                    Text(text = coin.price.toString())
                                }
                            }
                        }
                    }

                    CoinsViewState.Progress.Error -> Box(
                        modifier = Modifier
                            .background(Color.Red)
                            .fillMaxSize()
                    )
                }
            }
        }
    }
}