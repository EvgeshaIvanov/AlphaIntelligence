package com.presentation

import cafe.adriel.voyager.core.model.screenModelScope
import com.core.common.model.CoinEntity
import com.core.mvi.BaseViewModel
import com.presentation.contract.CryptoDetailAction
import com.presentation.contract.CryptoDetailEvent
import com.presentation.contract.CryptoDetailViewState
import kotlinx.coroutines.launch

class CryptoDetailViewModel(val coinEntity: CoinEntity) :
    BaseViewModel<CryptoDetailEvent, CryptoDetailViewState, CryptoDetailAction>() {
    override fun createInitialState(): CryptoDetailViewState = CryptoDetailViewState()

    init {
        screenModelScope.launch {
            try {
                setState {
                    copy(
                        coin = CryptoDetailViewState.Coin(
                            id = coinEntity.id,
                            name = coinEntity.name,
                            icon = coinEntity.icon,
                            symbol = coinEntity.symbol,
                            rank = coinEntity.rank,
                            price = "0",
                            indicator = coinEntity.indicators?.priceChange1d
                        ),
                        progress = CryptoDetailViewState.Progress.Content
                    )
                }
            } catch (e: Throwable) {
                setState {
                    copy(
                        progress = CryptoDetailViewState.Progress.Error
                    )
                }
            }
        }
    }

    override fun handleEvent(event: CryptoDetailEvent) {
        when (event) {
            CryptoDetailEvent.OnClose -> setAction { CryptoDetailAction.Close }
        }
    }

}