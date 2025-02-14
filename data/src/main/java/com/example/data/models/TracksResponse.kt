package com.example.data.models

import com.google.gson.annotations.SerializedName

data class TracksResponse(
    @SerializedName("tracks")
    val tracks: Tracks
)