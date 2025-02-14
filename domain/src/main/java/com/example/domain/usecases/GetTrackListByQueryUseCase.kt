package com.example.domain.usecases

import com.example.domain.repositories.TrackRepository
import javax.inject.Inject

class GetTrackListByQueryUseCase @Inject constructor(private val repository: TrackRepository) {
    operator fun invoke() = repository.getTrackListByQuery()
}