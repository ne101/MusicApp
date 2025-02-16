package com.example.musicapp.viewmodels

import androidx.lifecycle.viewModelScope
import com.example.domain.usecases.FetchTrackListByQueryUseCase
import com.example.domain.usecases.FetchTrackListUseCase
import com.example.domain.usecases.GetTrackListByQueryUseCase
import com.example.domain.usecases.GetTrackListUseCase
import com.example.musicapp.screen_states.ChartScreenState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class ChartViewModel @Inject constructor(
    private val getTrackListUseCase: GetTrackListUseCase,
    private val getTrackListByQueryUseCase: GetTrackListByQueryUseCase,
    private val fetchTrackListUseCase: FetchTrackListUseCase,
    private val fetchTrackListByQueryUseCase: FetchTrackListByQueryUseCase,
) : BaseViewModel() {

    private val _screenState = MutableStateFlow<ChartScreenState>(ChartScreenState.Initial)
    private val screenState = _screenState.asStateFlow()

    private val exceptionHandler = CoroutineExceptionHandler { _, message ->
        _screenState.update {
            ChartScreenState.Error(message.message ?: "Unknown error")
        }
    }
    private val trackListFlow = getTrackListUseCase.invoke().stateInViewModelScope(null)
    private val trackListFlowByQuery = getTrackListByQueryUseCase.invoke().stateInViewModelScope(null)
    init {
        fetchInitialData()
    }

    fun fetchInitialData() {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            fetchTrackListUseCase.invoke()
            trackListFlow.filterNotNull().map {
                ChartScreenState.MainContent(it)
            }.collect { newState ->
                _screenState.update {
                    newState
                }
            }
        }
    }

    fun fetchTracksByQuery(query: String) {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            fetchTrackListByQueryUseCase.invoke(query)
            trackListFlowByQuery.filterNotNull().map {
                ChartScreenState.MainContent(it)
            }.collect { newState ->
                _screenState.update {
                    newState
                }
            }
        }
    }



    fun getScreenState() = screenState

}