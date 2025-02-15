package com.example.musicapp.ui.screens

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import com.example.musicapp.navigation.AppNavGraph
import com.example.musicapp.navigation.Screen
import com.example.musicapp.navigation.rememberAppNavGraph
import com.example.musicapp.navigation.rememberNavigationState

@Composable
fun MainScreen() {
    val navigationState = rememberNavigationState()
    Scaffold(
        bottomBar = {}
    ) { paddingValues ->
        val graph = rememberAppNavGraph(
            navController = navigationState.navHostController,
            chartScreenContent = {
                ChartScreen(paddingValues) {
                    navigationState.navigateTo(Screen.TrackScreen(it))
                }
            },
            trackScreenContent = {
                TrackScreen(it, paddingValues)
            }
        )
        AppNavGraph(navigationState.navHostController, graph)
    }

}