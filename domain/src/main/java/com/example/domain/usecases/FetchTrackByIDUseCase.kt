package com.example.domain.usecases

import com.example.domain.repositories.TrackRepository
import javax.inject.Inject

class FetchTrackByIDUseCase @Inject constructor(private val repository: TrackRepository) {
    suspend operator fun invoke(id: Long) {
        repository.fetchTrackByID(id)
    }
}