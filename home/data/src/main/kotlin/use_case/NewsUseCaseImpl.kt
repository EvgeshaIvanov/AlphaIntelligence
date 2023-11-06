package use_case

import NewsRepository
import android.util.Log
import model.NewsEntity

class NewsUseCaseImpl(private val newsRepository: NewsRepository) : NewsUseCase {
    override suspend fun invoke(): NewsEntity? {
        return try {
            newsRepository.getNews()
        } catch (e: Throwable) {
            Log.i("eugeneData", e.toString())
            null
        }
    }
}