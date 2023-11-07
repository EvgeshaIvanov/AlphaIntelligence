package use_case

import model.CoinsEntity

interface CoinsUseCase {

    suspend fun invoke(): CoinsEntity?
}