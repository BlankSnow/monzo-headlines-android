package com.monzo.androidtest.common

import androidx.annotation.MainThread
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

abstract class BaseViewModel<S : Any>(initialState: S) : ViewModel() {
    protected val disposables: CompositeDisposable = CompositeDisposable()

    private val _state: MutableStateFlow<S> = MutableStateFlow(initialState)

    val state: StateFlow<S> get() = _state

    @MainThread
    protected fun setState(reducer: S.() -> S) {
        val currentState = _state.value
        val newState = currentState.reducer()
        if (newState != currentState) {
            _state.value = newState
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }
}
