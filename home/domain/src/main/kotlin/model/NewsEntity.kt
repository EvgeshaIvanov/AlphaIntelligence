package model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NewsEntity(
    val feeds: List<FeedEntity>,
)

@Serializable
data class FeedEntity(
    val title: String,
    val timePublished: String,
    val authors: List<String>,
)
