package com.presentation.contract

import com.core.mvi.UiAction

sealed interface CoinsAction: UiAction {
    object Close: CoinsAction
}
