package com.example.musicapp.di

import androidx.lifecycle.ViewModel
import com.example.musicapp.viewmodels.TrackViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface TrackViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(TrackViewModel::class)
    fun bindTrackViewModel(viewModel: TrackViewModel): ViewModel
}