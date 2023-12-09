package com.presentation.contract

import androidx.compose.runtime.Immutable
import com.core.mvi.UiState

@Immutable
data class CoinsViewState(
    val coins: List<Coin> = emptyList(),
    val progress: Progress = Progress.Loading
) : UiState {
    data class Coin(
        val id: String = "",
        val name: String = "",
        val icon: String = "",
        val symbol: String = "",
        val rank: Int = 0,
        val price: String = "$0.0",
        val indicator: Indicator? = null,
    )

    data class Indicator(
        val value: String,
        val state: State,
    ) {
        enum class State {
            Increase, Decrease
        }
    }

    enum class Progress {
        Loading, Content, Error
    }
}

internal fun Double.asIndicator(): CoinsViewState.Indicator {
    return CoinsViewState.Indicator(
        value = "$this%",
        state =  when {
            this >= 0.0 -> CoinsViewState.Indicator.State.Increase
            else -> CoinsViewState.Indicator.State.Decrease
        }
    )
}
