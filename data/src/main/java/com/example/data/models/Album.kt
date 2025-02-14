package com.example.data.models

import com.google.gson.annotations.SerializedName

data class Album(
    @SerializedName("id")
    val id: Int,
    @SerializedName("cover_medium")
    val cover: String
)
