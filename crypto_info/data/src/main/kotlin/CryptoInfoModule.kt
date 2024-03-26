import com.presentation.CryptoDetailViewModel
import com.presentation.CryptoDetailScreen
import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.instance
import org.kodein.di.new

val cryptoInfoModule = DI.Module(
    name = "cryptoInfoModule",
    init = {

        // ViewModel
        bindProvider<CryptoDetailViewModel> { new(::CryptoDetailViewModel) }

        // Screen
        bindProvider<CryptoDetailScreen> { CryptoDetailScreen(instance()) }
    }
)