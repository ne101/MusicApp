package com.example.musicapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavGraph
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.createGraph
import androidx.navigation.toRoute

@Composable
fun rememberAppNavGraph(
    navController: NavHostController,
    chartScreenContent: @Composable () -> Unit,
    trackScreenContent: @Composable (Long) -> Unit
): NavGraph {
    return remember(navController) {
        navController.createGraph(
            startDestination = Screen.ChartScreen
        ) {
            composable<Screen.ChartScreen> {
                chartScreenContent()
            }

            composable<Screen.TrackScreen> {
                val args = it.toRoute<Screen.TrackScreen>()
                trackScreenContent(args.id)
            }
        }
    }
}
