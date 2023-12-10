package com.core.common.model

import kotlinx.serialization.Serializable

@Serializable
data class CoinsEntity(
    val feeds: List<CoinEntity>,
)

@Serializable
data class CoinEntity(
    val id: String,
    val name: String,
    val icon: String,
    val symbol: String,
    val rank: Int,
    val price: Double,
    val indicators: Indicators? = null,
)

@Serializable
data class IndicatorItem(
    val value: String,
    val state: State,
) {
    enum class State {
        Increase, Decrease
    }
}

@Serializable
data class Indicators(
    val priceChange1h: IndicatorItem?,
    val priceChange1d: IndicatorItem?,
    val priceChange1w: IndicatorItem?,
)

fun Double?.asIndicator(): IndicatorItem? {
    return if (this != null)
        IndicatorItem(
            value = "$this%",
            state = when {
                this >= 0.0 -> IndicatorItem.State.Increase
                else -> IndicatorItem.State.Decrease
            }
        ) else null
}
