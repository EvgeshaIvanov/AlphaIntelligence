package com.core.mvi

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import com.core.mvi.time_machine.TimeCapsule
import com.core.mvi.time_machine.TimeTravelCapsule
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<Event : UiEvent, State : UiState, Action : UiAction> : ScreenModel {
    private val initialState: State by lazy { createInitialState() }
    abstract fun createInitialState(): State

    protected val currentState: State
        get() = uiState.value

    private val _uiState: MutableStateFlow<State> = MutableStateFlow(initialState)
    val uiState = _uiState.asStateFlow()

    private val _event: MutableSharedFlow<Event> = MutableSharedFlow()
    val event = _event.asSharedFlow()

    private val _action: Channel<Action> = Channel()
    val action = _action.receiveAsFlow()

    val timeCapsule: TimeCapsule<State> = TimeTravelCapsule { storedState ->
        _uiState.tryEmit(storedState)
    }


    init {
        subscribeEvents()
        timeCapsule.addState(initialState)
    }

    /**
     * Start listening to Event
     */
    private fun subscribeEvents() {
        coroutineScope.launch {
            event.collect {
                handleEvent(it)
            }
        }
    }

    /**
     * Handle each event
     */
    abstract fun handleEvent(event: Event)

    /**
     * Set new Event
     */
    fun setEvent(event: Event) {
        val newEvent = event
        coroutineScope.launch { _event.emit(newEvent) }
    }

    /**
     * Set new Ui State
     */
    protected fun setState(reduce: State.() -> State) {
        val newState = currentState.reduce()
        _uiState.value = newState
        timeCapsule.addState(newState)
    }

    /**
     * Set new Action
     */
    protected fun setAction(builder: () -> Action) {
        val effectValue = builder()
        coroutineScope.launch { _action.send(effectValue) }
    }
}
