import data_source.ktor.NewsKtorDataSource
import model.NewsEntity

class NewsRepositoryImpl(
    private val newsKtorDataSource: NewsKtorDataSource
) : NewsRepository {
    override suspend fun getNews(): NewsEntity {
        return newsKtorDataSource.getNews().toDomain()
    }
}