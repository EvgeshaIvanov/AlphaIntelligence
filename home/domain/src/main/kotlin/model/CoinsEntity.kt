package model

import kotlinx.serialization.Serializable

@Serializable
data class CoinsEntity(
    val feeds: List<CoinEntity>,
)

@Serializable
data class CoinEntity(
    val id: String,
    val icon: String,
    val symbol: String,
    val rank: Int,
    val price: Double,
)
