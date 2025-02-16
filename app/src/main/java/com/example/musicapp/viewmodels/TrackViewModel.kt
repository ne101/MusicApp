package com.example.musicapp.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.session.MediaController
import com.example.domain.usecases.FetchTrackByIDUseCase
import com.example.domain.usecases.GetTrackByIDUseCase
import com.example.musicapp.screen_states.ChartScreenState
import com.example.musicapp.screen_states.TrackScreenState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class TrackViewModel @Inject constructor(
    private val id: Long,
    private val getTrackByIDUseCase: GetTrackByIDUseCase,
    private val fetchTrackByIDUseCase: FetchTrackByIDUseCase,
) : BaseViewModel() {

    private val _screenState = MutableStateFlow<TrackScreenState>(TrackScreenState.Initial)
    private val screenState = _screenState.asStateFlow()

    private val exceptionHandler = CoroutineExceptionHandler { _, message ->
        _screenState.update {
            TrackScreenState.Error(message.message ?: "Unknown error")
        }
    }
    private val trackFlow = getTrackByIDUseCase.invoke().stateInViewModelScope(null)
    private val playingFlow = MutableStateFlow<Boolean>(false)
    private val playbackPositionFlow = MutableStateFlow(0L)
    private val durationFlow = MutableStateFlow(0L)

    private var positionUpdateJob: Job? = null

    init {
        fetchInitialData()
    }

    fun getScreenState() = screenState

    fun fetchInitialData() {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            fetchTrackByIDUseCase.invoke(id)
            combine(
                trackFlow.filterNotNull(),
                playingFlow,
                playbackPositionFlow,
                durationFlow
            ) { track, playing, playbackPosition, duration ->
                TrackScreenState.MainContent(track, playing, playbackPosition, duration)
            }.collect { newState ->
                _screenState.update {
                    newState
                }
            }
        }
    }


    fun updatePlayingState(state: Boolean) {
        playingFlow.update {
            state
        }
    }

    fun startPositionUpdate(mediaController: MediaController) {
        positionUpdateJob = viewModelScope.launch {
            while (true) {
                if (mediaController.isPlaying) {
                    val currentPosition = mediaController.currentPosition
                    playbackPositionFlow.update { currentPosition }
                }
                Log.d("startPositionUpdate", playbackPositionFlow.value.toString())
                delay(1000L)
            }
        }
    }

    fun stopPositionUpdate() {
        positionUpdateJob?.cancel()
        positionUpdateJob = null
    }

    fun updatePlaybackPosition(currentPosition: Long) {
        playbackPositionFlow.update {
            currentPosition
        }
    }

    fun updateDuration(duration: Long) {
        durationFlow.update {
            duration
        }
    }
}