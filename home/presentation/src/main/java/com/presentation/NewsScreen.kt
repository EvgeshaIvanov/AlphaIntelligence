package com.presentation

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import com.presentation.contract.NewsEvent
import com.presentation.contract.NewsViewState

class NewsScreen : Screen {
    @Composable
    override fun Content() {
        val newsViewModel = rememberScreenModel<NewsViewModel>()

        val state by newsViewModel.uiState.collectAsState()

        val navigator = LocalNavigator.currentOrThrow

        LaunchedEffect(Unit) {
            newsViewModel.setEvent(NewsEvent.OnCreate)
        }

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Crossfade(targetState = state.progress, label = "News") {
                when (it) {
                    NewsViewState.Progress.Loading -> Box(
                        modifier = Modifier
                            .background(Color.Blue)
                            .fillMaxSize()
                    )

                    NewsViewState.Progress.Content -> {
                        LazyColumn {
                            items(items = state.feeds) { feed ->
                                Text(text = feed.title)
                            }
                        }
                    }

                    NewsViewState.Progress.Error -> Box(
                        modifier = Modifier
                            .background(Color.Red)
                            .fillMaxSize()
                    )
                }
            }
        }
    }
}