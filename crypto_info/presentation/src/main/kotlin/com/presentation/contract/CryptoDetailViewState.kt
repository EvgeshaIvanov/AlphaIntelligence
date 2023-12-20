package com.presentation.contract

import androidx.compose.runtime.Immutable
import com.core.common.model.Indicators
import com.core.mvi.UiState

@Immutable
data class CryptoDetailViewState(
    val coin: Coin = Coin(),
    val progress: Progress = Progress.Loading
) : UiState {
    data class Coin(
        val id: String = "",
        val name: String = "",
        val icon: String = "",
        val symbol: String = "",
        val rank: Int = 0,
        val price: String = "$0.0",
        val indicators: Indicators? = null,
    )

    enum class Progress {
        Loading, Content, Error
    }
}

fun CryptoDetailViewState.Coin.toChartsData() = listOf(
    this.indicators?.priceChange1h?.value ?: 0f,
    this.indicators?.priceChange1w?.value ?: 0f,
    this.indicators?.priceChange1d?.value ?: 0f,
)
