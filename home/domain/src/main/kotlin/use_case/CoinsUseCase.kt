package use_case

import com.core.common.model.CoinsEntity

interface CoinsUseCase {

    suspend operator fun invoke(): CoinsEntity?
}