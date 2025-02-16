package com.example.musicapp.ui.screens


import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.domain.entities.TracksEntity
import com.example.musicapp.R
import com.example.musicapp.app.getApplicationComponent
import com.example.musicapp.screen_states.ChartScreenState
import com.example.musicapp.ui.common.CustomTextField
import com.example.musicapp.ui.common.ErrorScreen
import com.example.musicapp.ui.common.InitialScreen
import com.example.musicapp.ui.common.TrackCard
import com.example.musicapp.viewmodels.ChartViewModel

@Composable
fun ChartScreen(
    paddingValues: PaddingValues,
    onTrackClick: (Long) -> Unit,
) {
    val component = getApplicationComponent()
    val viewModel: ChartViewModel = viewModel(factory = component.getViewModelFactory())
    val screenState = viewModel.getScreenState().collectAsStateWithLifecycle()
    ScreenState(
        screenState = screenState,
        paddingValues = paddingValues,
        viewModel = viewModel,
        onTrackClick = onTrackClick
    )
}

@Composable
private fun ScreenState(
    screenState: State<ChartScreenState>,
    paddingValues: PaddingValues,
    viewModel: ChartViewModel,
    onTrackClick: (Long) -> Unit,
) {
    when (val currentScreenState = screenState.value) {
        is ChartScreenState.MainContent -> {
            MainContent(
                tracksEntity = currentScreenState.tracksEntity,
                paddingValues = paddingValues,
                viewModel = viewModel,
                onTrackClick = onTrackClick
            )
        }

        is ChartScreenState.Error -> {
            ErrorScreen(currentScreenState.message)
        }

        ChartScreenState.Initial -> {
            InitialScreen()
        }

    }
}

@Composable
private fun MainContent(
    tracksEntity: TracksEntity,
    paddingValues: PaddingValues,
    viewModel: ChartViewModel,
    onTrackClick: (Long) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        CustomTextField(
            textPlaceHolder = stringResource(R.string.search_track),
            name = {
                if (it.isNotBlank()) {
                    viewModel.fetchTracksByQuery(it)

                }
            },
            exit = {viewModel.fetchInitialData()}
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            items(tracksEntity.trackList, key = { it.id }) {
                TrackCard(it) { id ->
                    onTrackClick(id)
                }

            }
        }
    }

}

