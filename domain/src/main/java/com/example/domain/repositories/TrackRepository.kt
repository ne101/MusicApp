package com.example.domain.repositories

import com.example.domain.entities.TrackEntity
import com.example.domain.entities.TracksEntity
import kotlinx.coroutines.flow.Flow


interface TrackRepository {

    fun getTrackList(): Flow<TracksEntity>
    suspend fun fetchTrackList()

    fun getTrackByID(): Flow<TrackEntity>
    suspend fun fetchTrackByID(id: Long)

    fun getTrackListByQuery(): Flow<TracksEntity>
    suspend fun fetchTrackListByQuery(query: String)

}