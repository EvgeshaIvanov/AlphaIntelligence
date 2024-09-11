package com.presentation

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.kodein.rememberScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.core.compose.CoinsLoader
import com.core.compose.rememberClick
import com.core.theme.CryptoColors
import com.presentation.contract.CoinsAction
import com.presentation.contract.CoinsEvent
import com.presentation.contract.CoinsViewState
import com.presentation.ui.CoinsList
import kotlinx.coroutines.flow.collectLatest

class CoinsScreen : Screen {

    @Composable
    override fun Content() {
        val viewModel = rememberScreenModel<CoinsViewModel>()
        val handler = rememberClick(viewModel::handleEvent)

        val state by viewModel.uiState.collectAsState()

        val navigator = LocalNavigator.currentOrThrow

        LaunchedEffect(viewModel.action) {
            viewModel.action.collectLatest { action ->
                when (action) {
                    is CoinsAction.OpenDetailScreen -> navigator.push(CryptoDetailScreen(action.coinEntity))
                }
            }
        }

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = CryptoColors.BackgroundPrimary
        ) {
            Crossfade(
                label = "News",
                targetState = state.progress
            ) { visibleState ->
                when (visibleState) {
                    CoinsViewState.Progress.Loading -> {
                        Box(
                            Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CoinsLoader()
                        }
                    }

                    CoinsViewState.Progress.Content -> {
                        CoinsList(
                            coins = state.coins,
                            onItemClick = { coin ->
                                handler.invoke(CoinsEvent.OnItemClick(coin))
                            }
                        )
                    }

                    CoinsViewState.Progress.Error -> {
                        // TODO
                    }
                }
            }
        }
    }
}