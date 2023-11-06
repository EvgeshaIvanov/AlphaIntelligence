import com.presentation.NewsScreen
import com.presentation.NewsViewModel
import data_source.ktor.NewsKtorDataSource
import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.new
import use_case.NewsUseCase
import use_case.NewsUseCaseImpl

val homeModule = DI.Module(
    name = "homeModule",
    init = {

        // DataSource
        bindProvider<NewsKtorDataSource> { new(::NewsKtorDataSource) }

        // Repository
        bindProvider<NewsRepository> { new(::NewsRepositoryImpl) }

        //UseCase
        bindProvider<NewsUseCase> { new(::NewsUseCaseImpl) }

        //
        bindProvider<NewsViewModel> { new(::NewsViewModel) }

        bindProvider<NewsScreen> { new(::NewsScreen) }
    }
)