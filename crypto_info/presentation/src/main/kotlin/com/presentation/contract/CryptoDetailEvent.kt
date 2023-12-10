package com.presentation.contract

import com.core.mvi.UiEvent

sealed interface CryptoDetailEvent: UiEvent {

    object OnCreate : CryptoDetailEvent
}