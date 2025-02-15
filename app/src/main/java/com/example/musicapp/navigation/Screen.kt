package com.example.musicapp.navigation

import kotlinx.serialization.Serializable

sealed class Screen {
    @Serializable
    data object ChartScreen : Screen()
    @Serializable
    data class TrackScreen(val id: Long) : Screen()
}