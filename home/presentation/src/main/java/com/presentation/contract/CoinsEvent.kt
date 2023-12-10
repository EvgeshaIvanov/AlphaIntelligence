package com.presentation.contract

import com.core.mvi.UiEvent

sealed interface CoinsEvent: UiEvent {

    object OnCreate : CoinsEvent

    class OnItemClick(val coinEntity: CoinsViewState.Coin): CoinsEvent
}