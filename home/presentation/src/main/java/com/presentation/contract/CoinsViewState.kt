package com.presentation.contract

import androidx.compose.runtime.Immutable
import com.core.common.model.Indicators
import com.core.mvi.UiState

@Immutable
data class CoinsViewState(
    val coins: List<Coin> = emptyList(),
    val progress: Progress = Progress.Loading
) : UiState {

    @Immutable
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
