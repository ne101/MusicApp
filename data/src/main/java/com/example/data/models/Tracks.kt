package com.example.data.models

import com.google.gson.annotations.SerializedName

data class Tracks(
    @SerializedName("data")
    val trackList: List<TrackResponse>
)