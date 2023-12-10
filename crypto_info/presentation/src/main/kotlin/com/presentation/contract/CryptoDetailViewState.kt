package com.presentation.contract

import androidx.compose.runtime.Immutable
import com.core.common.model.IndicatorItem
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
        val indicator: IndicatorItem? = null,
    )

    enum class Progress {
        Loading, Content, Error
    }
}
