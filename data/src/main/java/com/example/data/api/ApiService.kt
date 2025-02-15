package com.example.data.api

import com.example.data.models.TrackResponse
import com.example.data.models.TracksResponse

import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("chart")
    suspend fun getTrackList(): TracksResponse

    @GET("track/{id}")
    suspend fun getTrackByID(@Path("id") id: Long): TrackResponse

    @GET("search?q={query}")
    suspend fun getTrackListByQuery(@Path("query") query: String): TracksResponse
}