import data_source.ktor.CoinsKtorDataSource
import com.core.common.model.CoinsEntity

class CoinsRepositoryImpl(
    private val coinsKtorDataSource: CoinsKtorDataSource
) : CoinsRepository {
    override suspend fun getCoins(): CoinsEntity {
        return coinsKtorDataSource.getCoins().toDomain()
    }
}