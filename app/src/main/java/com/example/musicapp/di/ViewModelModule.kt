package com.example.musicapp.di

import androidx.lifecycle.ViewModel
import com.example.musicapp.viewmodels.ChartViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(ChartViewModel::class)
    fun bindChartViewModel(viewModel: ChartViewModel): ViewModel
}