package com.presentation.contract

import com.core.mvi.UiAction

sealed interface NewsAction: UiAction {
    object Close: NewsAction
}
