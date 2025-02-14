package com.example.musicapp.di

import com.example.data.di.ApplicationScope
import com.example.data.di.DataModule
import com.example.musicapp.viewmodels.ViewModelFactory
import dagger.Component

@ApplicationScope
@Component(
    modules = [
        DataModule::class,
        ViewModelModule::class
    ]
)
interface ApplicationComponent {
    fun getViewModelFactory(): ViewModelFactory
}