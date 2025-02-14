package com.example.domain.usecases

import com.example.domain.entities.TracksEntity
import com.example.domain.repositories.TrackRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTrackListUseCase @Inject constructor(private val repository: TrackRepository) {
    operator fun invoke() = repository.getTrackList()
}