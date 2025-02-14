package com.example.data.models

import com.google.gson.annotations.SerializedName

data class TrackResponse(
    @SerializedName("artist")
    val artist: Artist,
    @SerializedName("album")
    val album: Album,
    @SerializedName("duration")
    val duration: Int,
    @SerializedName("id")
    val id: Long,
    @SerializedName("preview")
    val preview: String,
    @SerializedName("title")
    val title: String,
)