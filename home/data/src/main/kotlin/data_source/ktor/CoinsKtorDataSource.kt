package data_source.ktor

import com.data.BuildConfig
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.url

const val BASE_URL = "https://openapiv1.coinstats.app/coins"

class CoinsKtorDataSource(
    private val httpClient: HttpClient
) {
    suspend fun getCoins(): CoinsKtorResponse {
        return httpClient.get {
            url(BASE_URL)
            header("X-API-KEY", BuildConfig.API_KEY)
        }.body<CoinsKtorResponse>()
    }
}