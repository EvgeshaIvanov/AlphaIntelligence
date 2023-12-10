package com.presentation.contract

import com.core.common.model.CoinEntity
import com.core.mvi.UiAction

sealed interface CoinsAction: UiAction {
    class OpenDetailScreen(val coinEntity: CoinEntity): CoinsAction
}
