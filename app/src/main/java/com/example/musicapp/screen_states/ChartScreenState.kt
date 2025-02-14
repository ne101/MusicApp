package com.example.musicapp.screen_states

import com.example.domain.entities.TracksEntity

sealed class ChartScreenState {
    data object Initial : ChartScreenState()
    data class MainContent(val tracksEntity: TracksEntity) : ChartScreenState()
    data class Error(val message: String) : ChartScreenState()
}