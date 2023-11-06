import model.NewsEntity

interface NewsRepository {

    suspend fun getNews(): NewsEntity
}