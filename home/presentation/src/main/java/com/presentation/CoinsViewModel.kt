package com.presentation

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import cafe.adriel.voyager.core.model.coroutineScope
import com.core.common.formatPrice
import com.core.mvi.BaseViewModel
import com.presentation.contract.CoinsAction
import com.presentation.contract.CoinsEvent
import com.presentation.contract.CoinsViewState
import com.presentation.contract.asIndicator
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import use_case.CoinsUseCase

class CoinsViewModel(private val useCase: CoinsUseCase) :
    BaseViewModel<CoinsEvent, CoinsViewState, CoinsAction>() {

    override fun createInitialState(): CoinsViewState = CoinsViewState()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun handleEvent(event: CoinsEvent) {
        when (event) {
            CoinsEvent.OnCreate -> coroutineScope.launch {
                loadData()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private suspend fun loadData() {
        try {
            val result = useCase.invoke()

            setState {
                this.copy(
                    coins = result?.feeds?.map { coin ->
                        CoinsViewState.Coin(
                            id = coin.id,
                            name = coin.name,
                            icon = coin.icon,
                            symbol = coin.symbol,
                            rank = coin.rank,
                            price = formatPrice(coin.price),
                            indicator = coin.priceChange1d.asIndicator()
                        )
                    } ?: emptyList(),
                    progress = CoinsViewState.Progress.Content
                )
            }
        } catch (e: Throwable) {
            Log.i("EugeneData", e.toString())
            setState {
                copy(
                    progress = CoinsViewState.Progress.Error
                )
            }
        }
    }

}