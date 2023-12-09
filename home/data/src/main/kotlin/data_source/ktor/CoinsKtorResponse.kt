package data_source.ktor

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import model.CoinEntity
import model.CoinsEntity

@Serializable
data class CoinsKtorResponse(
    @SerialName("result") val coins: List<CoinKtorResponse>? = null,
) {
    fun toDomain(): CoinsEntity {
        return CoinsEntity(
            feeds = coins?.mapNotNull { it.toDomain() } ?: emptyList()
        )
    }
}

@Serializable
data class CoinKtorResponse(
    @SerialName("id") val id: String? = null,
    @SerialName("name") val name: String? = null,
    @SerialName("icon") val icon: String? = null,
    @SerialName("symbol") val symbol: String? = null,
    @SerialName("rank") val rank: Int? = null,
    @SerialName("price") val price: Double? = null,
    @SerialName("priceChange1h") val priceChange1h: Double? = null,
    @SerialName("priceChange1d") val priceChange1d: Double? = null,
    @SerialName("priceChange1w") val priceChange1w: Double? = null,
) {
    fun toDomain(): CoinEntity? {
        return CoinEntity(
            id = id ?: return null,
            name = name ?: return null,
            icon = icon ?: return null,
            symbol = symbol ?: return null,
            price = price ?: return null,
            rank = rank ?: return null,
            priceChange1h = priceChange1h ?: return null,
            priceChange1d = priceChange1d ?: return null,
            priceChange1w = priceChange1w ?: return null,
        )
    }
}