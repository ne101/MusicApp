package com.example.musicapp.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.domain.entities.TracksEntity
import com.example.musicapp.app.getApplicationComponent
import com.example.musicapp.screen_states.ChartScreenState
import com.example.musicapp.ui.common.TrackCard
import com.example.musicapp.viewmodels.ChartViewModel

@Composable
fun ChartScreen(
    paddingValues: PaddingValues,
) {
    val component = getApplicationComponent()
    val viewModel: ChartViewModel = viewModel(factory = component.getViewModelFactory())
    val screenState = viewModel.getScreenState().collectAsStateWithLifecycle()
    ScreenState(
        screenState,
        paddingValues
    )
    Log.d("ChartScreen", screenState.value.toString())

}

@Composable
private fun ScreenState(
    screenState: State<ChartScreenState>,
    paddingValues: PaddingValues
) {
    when (val currentScreenState = screenState.value) {
        is ChartScreenState.MainContent -> {
            MainContent(
                currentScreenState.tracksEntity,
                paddingValues
            )
        }

        is ChartScreenState.Error -> {

        }

        ChartScreenState.Initial -> {}

    }
}

@Composable
private fun MainContent(
    tracksEntity: TracksEntity,
    paddingValues: PaddingValues
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = paddingValues
    ) {
        items(tracksEntity.trackList, key = { it.id }) {
            TrackCard(it) {

            }

        }
    }
}

