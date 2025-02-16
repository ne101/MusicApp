package com.example.data.api

import com.example.data.models.TrackResponse
import com.example.data.models.Tracks
import com.example.data.models.TracksResponse

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("chart")
    suspend fun getTrackList(): TracksResponse

    @GET("track/{id}")
    suspend fun getTrackByID(@Path("id") id: Long): TrackResponse

    @GET("search")
    suspend fun getTrackListByQuery(@Query("q") query: String): Tracks
}