package use_case

import CoinsRepository
import android.util.Log
import model.CoinsEntity

class CoinsUseCaseImpl(private val coinsRepository: CoinsRepository) : CoinsUseCase {
    override suspend fun invoke(): CoinsEntity? {
        return try {
            coinsRepository.getCoins()
        } catch (e: Throwable) {
            Log.i("eugeneData", e.toString())
            null
        }
    }
}