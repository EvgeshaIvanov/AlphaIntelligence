import model.CoinsEntity

interface CoinsRepository {

    suspend fun getCoins(): CoinsEntity
}