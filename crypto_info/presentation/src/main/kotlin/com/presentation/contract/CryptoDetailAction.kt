package com.presentation.contract

import com.core.mvi.UiAction

sealed interface CryptoDetailAction: UiAction {
    data object Close: CryptoDetailAction
}
