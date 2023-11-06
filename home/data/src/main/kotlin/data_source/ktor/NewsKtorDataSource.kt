package data_source.ktor

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class NewsKtorDataSource(
    private val httpClient: HttpClient
) {

    suspend fun getNews(): NewsKtorResponse {
        return httpClient.get("https://www.alphavantage.co/query?function=NEWS_SENTIMENT&tickers=AAPL&apikey=E1L40KWFWPO8A971")
            .body<NewsKtorResponse>()
    }
}