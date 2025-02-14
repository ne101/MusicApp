package com.example.domain.usecases

import com.example.domain.repositories.TrackRepository
import javax.inject.Inject

class FetchTrackListByQueryUseCase @Inject constructor(private val repository: TrackRepository) {
    suspend operator fun invoke(query: String) {
        repository.fetchTrackListByQuery(query)
    }
}