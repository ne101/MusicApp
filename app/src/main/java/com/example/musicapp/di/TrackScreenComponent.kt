package com.example.musicapp.di

import com.example.musicapp.viewmodels.ViewModelFactory
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(modules = [TrackViewModelModule::class])
interface TrackScreenComponent {

    fun getViewModelFactory(): ViewModelFactory

    @Subcomponent.Factory
    interface Factory {
        fun create(
            @BindsInstance
            id: Long
        ): TrackScreenComponent
    }

}