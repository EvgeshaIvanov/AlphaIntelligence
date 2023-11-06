package com.presentation.contract

import com.core.mvi.UiEvent

sealed interface NewsEvent: UiEvent {

    object OnCreate : NewsEvent
}