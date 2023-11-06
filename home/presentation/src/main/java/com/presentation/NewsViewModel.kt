package com.presentation

import cafe.adriel.voyager.core.model.coroutineScope
import com.core.mvi.BaseViewModel
import com.presentation.contract.NewsAction
import com.presentation.contract.NewsEvent
import com.presentation.contract.NewsViewState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import use_case.NewsUseCase

class NewsViewModel(private val useCase: NewsUseCase) :
    BaseViewModel<NewsEvent, NewsViewState, NewsAction>() {

    override fun createInitialState(): NewsViewState = NewsViewState()

    override fun handleEvent(event: NewsEvent) {
        when (event) {
            NewsEvent.OnCreate -> coroutineScope.launch {
                loadData()
            }
        }
    }

    private suspend fun loadData() {
        try {
            val result = useCase.invoke()
            delay(2000)
            setState {
                this.copy(
                    feeds = result?.feeds?.map {
                        NewsViewState.Feed(
                            title = it.title,
                            timePublished = it.timePublished,
                            authors = it.authors
                        )
                    } ?: emptyList(),
                    progress = NewsViewState.Progress.Content
                )
            }
        } catch (e: Throwable) {
            setState {
                copy(
                    progress = NewsViewState.Progress.Error
                )
            }
        }
    }

}