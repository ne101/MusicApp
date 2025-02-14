package com.example.data.di

import com.example.data.api.ApiFactory
import com.example.data.api.ApiService
import com.example.data.repositories.TrackRepositoryImpl
import com.example.domain.repositories.TrackRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @ApplicationScope
    @Binds
    fun bindTrackRepository(impl: TrackRepositoryImpl): TrackRepository

    companion object {
        @ApplicationScope
        @Provides
        fun provideApiService(): ApiService = ApiFactory.apiService
    }
}