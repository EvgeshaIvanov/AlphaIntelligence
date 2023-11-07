import com.presentation.CoinsScreen
import com.presentation.CoinsViewModel
import data_source.ktor.CoinsKtorDataSource
import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.new
import use_case.CoinsUseCase
import use_case.CoinsUseCaseImpl

val homeModule = DI.Module(
    name = "homeModule",
    init = {

        // DataSource
        bindProvider<CoinsKtorDataSource> { new(::CoinsKtorDataSource) }

        // Repository
        bindProvider<CoinsRepository> { new(::CoinsRepositoryImpl) }

        //UseCase
        bindProvider<CoinsUseCase> { new(::CoinsUseCaseImpl) }

        //
        bindProvider<CoinsViewModel> { new(::CoinsViewModel) }

        bindProvider<CoinsScreen> { new(::CoinsScreen) }
    }
)