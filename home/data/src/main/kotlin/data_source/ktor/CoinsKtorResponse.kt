package data_source.ktor

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import model.CoinEntity
import model.CoinsEntity

@Serializable
data class CoinsKtorResponse(
    @SerialName("result") val coins: List<CoinKtorResponse>,
) {
    fun toDomain(): CoinsEntity {
        return CoinsEntity(
            feeds = coins.map { it.toDomain() }
        )
    }
}

@Serializable
data class CoinKtorResponse(
    @SerialName("id") val id: String,
    @SerialName("icon") val icon: String,
    @SerialName("symbol") val symbol: String,
    @SerialName("rank") val rank: Int,
    @SerialName("price") val price: Double,
) {
    fun toDomain(): CoinEntity {
        return CoinEntity(
            id = id,
            icon = icon,
            symbol = symbol,
            price = price,
            rank = rank,
        )
    }
}