package com.example.domain.usecases

import com.example.domain.repositories.TrackRepository
import javax.inject.Inject

class FetchTrackListUseCase @Inject constructor(private val repository: TrackRepository) {
    suspend operator fun invoke() {
        repository.fetchTrackList()
    }
}