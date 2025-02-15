package com.example.data.repositories

import com.example.data.api.ApiService
import com.example.data.mapper.Mapper.toDomain
import com.example.domain.entities.TrackEntity
import com.example.domain.entities.TracksEntity
import com.example.domain.repositories.TrackRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

class TrackRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : TrackRepository {
    private val trackFlow = MutableSharedFlow<TrackEntity>(replay = 1)
    private val trackListFlow = MutableSharedFlow<TracksEntity>(replay = 1)
    private val trackListByQueryFlow = MutableSharedFlow<TracksEntity>(replay = 1)

    override fun getTrackList() = trackListFlow.asSharedFlow()

    override suspend fun fetchTrackList() {
        val response = apiService.getTrackList().toDomain()
        trackListFlow.emit(response)
    }

    override fun getTrackByID() = trackFlow.asSharedFlow()

    override suspend fun fetchTrackByID(id: Long) {
        val response = apiService.getTrackByID(id).toDomain()
        trackFlow.emit(response)
    }

    override fun getTrackListByQuery() = trackListByQueryFlow.asSharedFlow()

    override suspend fun fetchTrackListByQuery(query: String) {
        val response = apiService.getTrackListByQuery(query).toDomain()
        trackListByQueryFlow.emit(response)
    }
}