package com.presentation

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import com.core.common.model.CoinEntity
import com.core.compose.CoinsLoader
import com.core.theme.CryptoColors
import com.presentation.contract.CryptoDetailViewState
import com.presentation.ui.CryptoDetailHeader

class CryptoDetailScreen(private val coinEntity: CoinEntity) : Screen {

    @Composable
    override fun Content() {
        val viewModel = rememberScreenModel { CryptoDetailViewModel(coinEntity = coinEntity) }

        val state by viewModel.uiState.collectAsState()

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = CryptoColors.BackgroundPrimary
        ) {
            Crossfade(targetState = state.progress, label = "visibleState") { visibleState ->
                when (visibleState) {
                    CryptoDetailViewState.Progress.Loading -> {
                        Box(contentAlignment = Alignment.Center) {
                            CoinsLoader()
                        }
                    }

                    CryptoDetailViewState.Progress.Content -> {
                        CryptoDetailHeader(
                            imageUrl = state.coin.icon,
                            title = state.coin.name,
                            price = "0.0",
                            indicator = state.coin.indicator
                        )
                    }

                    CryptoDetailViewState.Progress.Error -> {

                    }
                }
            }
        }

    }
}