package com.presentation

import cafe.adriel.voyager.core.model.screenModelScope
import com.core.common.formatPrice
import com.core.common.model.CoinEntity
import com.core.mvi.BaseViewModel
import com.presentation.contract.CoinsAction
import com.presentation.contract.CoinsEvent
import com.presentation.contract.CoinsViewState
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.launch
import use_case.CoinsUseCase

class CoinsViewModel(
    private val coinsUseCase: CoinsUseCase,
) : BaseViewModel<CoinsEvent, CoinsViewState, CoinsAction>() {

    override fun createInitialState(): CoinsViewState = CoinsViewState()

    init {
        screenModelScope.launch {
            runCatching { coinsUseCase()?.feeds }
                .onSuccess { feeds ->
                    setState {
                        copy(
                            coins = feeds?.map(::toCoinsViewState).orEmpty().toImmutableList(),
                            progress = CoinsViewState.Progress.Content
                        )
                    }
                }
                .onFailure {
                    setState {
                        copy(progress = CoinsViewState.Progress.Error)
                    }
                }
        }
    }

    override fun handleEvent(event: CoinsEvent) {
        when (event) {
            is CoinsEvent.OnItemClick ->
                setAction {
                    CoinsAction.OpenDetailScreen(
                        coinEntity = CoinEntity(
                            id = event.coinEntity.id,
                            name = event.coinEntity.name,
                            icon = event.coinEntity.icon,
                            symbol = event.coinEntity.symbol,
                            rank = event.coinEntity.rank,
                            price = 0.0,
                            indicators = event.coinEntity.indicators
                        )
                    )
                }
        }
    }

    private fun toCoinsViewState(coinEntity: CoinEntity) = CoinsViewState.Coin(
        id = coinEntity.id,
        name = coinEntity.name,
        icon = coinEntity.icon,
        symbol = coinEntity.symbol,
        rank = coinEntity.rank,
        price = formatPrice(coinEntity.price),
        indicators = coinEntity.indicators,
    )
}