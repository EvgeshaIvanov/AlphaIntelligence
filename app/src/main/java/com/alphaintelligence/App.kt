package com.alphaintelligence

import android.app.Application
import com.core.ktor.ktorModule
import homeModule
import org.kodein.di.DI
import org.kodein.di.DIAware

class App: Application(), DIAware {

    override val di: DI by DI.lazy {
        import(ktorModule)
        import(homeModule)
    }
}