package com.presentation.contract

import com.core.mvi.UiAction

sealed interface CryptoDetailAction: UiAction {
    object Close: CryptoDetailAction
}
