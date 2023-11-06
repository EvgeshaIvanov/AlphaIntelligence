package com.presentation

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import cafe.adriel.voyager.core.model.coroutineScope
import com.core.mvi.BaseViewModel
import com.presentation.contract.NewsAction
import com.presentation.contract.NewsEvent
import com.presentation.contract.NewsViewState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import use_case.NewsUseCase
import java.sql.Time
import java.text.DateFormat
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class NewsViewModel(private val useCase: NewsUseCase) :
    BaseViewModel<NewsEvent, NewsViewState, NewsAction>() {

    override fun createInitialState(): NewsViewState = NewsViewState()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun handleEvent(event: NewsEvent) {
        when (event) {
            NewsEvent.OnCreate -> coroutineScope.launch {
                loadData()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private suspend fun loadData() {
        try {
            val result = useCase.invoke()
            delay(2000)

            setState {
                this.copy(
                    feeds = result?.feeds?.map {
                        val format = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss")
                        val data = LocalDate.parse(it.timePublished, format)
                        NewsViewState.Feed(
                            title = it.title,
                            timePublished = "${data.dayOfMonth} ${data.month.toString().lowercase()} ${data.year}",
                            authors = it.authors
                        )
                    } ?: emptyList(),
                    progress = NewsViewState.Progress.Content
                )
            }
        } catch (e: Throwable) {
            Log.i("EugeneData", e.toString())
            setState {
                copy(
                    progress = NewsViewState.Progress.Error
                )
            }
        }
    }

}