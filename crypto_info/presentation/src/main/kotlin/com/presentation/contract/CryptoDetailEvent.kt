package com.presentation.contract

import com.core.mvi.UiEvent

sealed interface CryptoDetailEvent: UiEvent {

    data object OnClose : CryptoDetailEvent
}