package com.presentation.contract

import com.core.mvi.UiState

data class CoinsViewState(
    val coins: List<Coin> = emptyList(),
    val progress: Progress = Progress.Loading
) : UiState {
    data class Coin(
        val id: String = "",
        val icon: String = "",
        val symbol: String = "",
        val rank: Int = 0,
        val price: Double = 0.0,
    )

    enum class Progress {
        Loading, Content, Error
    }
}
