package com.presentation.contract

import com.core.mvi.UiEvent

sealed interface CoinsEvent: UiEvent {

    class OnItemClick(val coinEntity: CoinsViewState.Coin): CoinsEvent
}