package com.example.musicapp.screen_states

import com.example.domain.entities.TrackEntity

sealed class TrackScreenState {
    data object Initial : TrackScreenState()
    data class MainContent(
        val trackEntity: TrackEntity,
        val playing: Boolean,
        val playbackPosition: Long,
        val duration: Long
    ) : TrackScreenState()
    data class Error(val message: String) : TrackScreenState()
}