package data_source.ktor

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import model.FeedEntity
import model.NewsEntity

@Serializable
data class NewsKtorResponse(
    @SerialName("feed") val feeds: List<NewsFeedKtorResponse>,
) {
    fun toDomain(): NewsEntity {
        return NewsEntity(
            feeds = feeds.map { it.toDomain() }
        )
    }
}

@Serializable
data class NewsFeedKtorResponse(
    @SerialName("title") val title: String,
    @SerialName("time_published") val timePublished: String,
    @SerialName("authors") val authors: List<String>,
) {
    fun toDomain(): FeedEntity {
        return FeedEntity(
            title = title,
            timePublished = timePublished,
            authors = authors
        )
    }
}