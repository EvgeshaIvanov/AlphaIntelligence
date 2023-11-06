package com.presentation.contract

import com.core.mvi.UiState

data class NewsViewState(
    val feeds: List<Feed> = emptyList(),
    val progress: Progress = Progress.Loading
) : UiState {
    data class Feed(
        val title: String = "",
        val timePublished: String = "",
        val authors: List<String> = emptyList(),
    )

    enum class Progress {
        Loading, Content, Error
    }
}
