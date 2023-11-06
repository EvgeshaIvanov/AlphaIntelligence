package use_case

import model.NewsEntity

interface NewsUseCase {

    suspend fun invoke(): NewsEntity?
}