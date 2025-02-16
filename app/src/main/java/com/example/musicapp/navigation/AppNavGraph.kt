package com.example.musicapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraph
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun AppNavGraph(
    navHostController: NavHostController,
    graph: NavGraph
) {
    NavHost(
        navController = navHostController,
        graph = graph
    )
}