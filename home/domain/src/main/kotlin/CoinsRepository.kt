import com.core.common.model.CoinsEntity

interface CoinsRepository {

    suspend fun getCoins(): CoinsEntity
}