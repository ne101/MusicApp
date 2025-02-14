package com.example.musicapp.app

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.example.musicapp.di.ApplicationComponent
import com.example.musicapp.di.DaggerApplicationComponent

class App : Application() {
    val component: ApplicationComponent by lazy {
        DaggerApplicationComponent.create()
    }
}
@Composable
fun getApplicationComponent(): ApplicationComponent {
    return (LocalContext.current.applicationContext as App).component
}