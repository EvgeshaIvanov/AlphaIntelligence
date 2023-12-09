package model

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
    val priceChange1h: Double,
    val priceChange1d: Double,
    val priceChange1w: Double,
)
