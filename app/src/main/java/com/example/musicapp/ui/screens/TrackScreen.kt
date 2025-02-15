package com.example.musicapp.ui.screens

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.musicapp.R
import com.example.musicapp.app.getApplicationComponent
import com.example.musicapp.screen_states.TrackScreenState
import com.example.musicapp.service.MediaService
import com.example.musicapp.ui.common.TextArtistName
import com.example.musicapp.ui.common.TextTrackName
import com.example.musicapp.viewmodels.TrackViewModel

@Composable
fun TrackScreen(id: Long, paddingValues: PaddingValues) {
    val component = getApplicationComponent().getTrackScreenComponentFactory().create(id)
    val viewModel: TrackViewModel = viewModel(factory = component.getViewModelFactory())
    val screenState = viewModel.getScreenState().collectAsStateWithLifecycle()
    when (val currentScreenState = screenState.value) {
        is TrackScreenState.MainContent -> {
            MainContent(currentScreenState, paddingValues, viewModel)
        }

        is TrackScreenState.Error -> {

        }

        TrackScreenState.Initial -> {}
    }
}


@Composable
private fun MainContent(
    currentScreenState: TrackScreenState.MainContent,
    paddingValues: PaddingValues,
    viewModel: TrackViewModel
) {
    val context = LocalContext.current

    LaunchedEffect(null) {
        val intent = Intent(context, MediaService::class.java).apply {
            putExtra("MUSIC_URL", currentScreenState.trackEntity.preview)
        }
        context.startService(intent)
    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(horizontal = 16.dp)
    ) {
        AsyncImage(
            currentScreenState.trackEntity.albumEntity.cover,
            contentDescription = null,
            modifier = Modifier.size(256.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextTrackName(currentScreenState.trackEntity.title, 24)
        Spacer(modifier = Modifier.height(8.dp))
        TextArtistName(currentScreenState.trackEntity.artist.name, 16)



        Box(modifier = Modifier) {
            val image = if (currentScreenState.playing) {
                R.drawable.pause_48dp_e8eaed_fill0_wght400_grad0_opsz48
            } else {
                R.drawable.play_arrow_48dp_e8eaed_fill0_wght400_grad0_opsz48
            }
            Icon(
                imageVector = ImageVector.vectorResource(image) ,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .clip(RoundedCornerShape(32.dp))
                    .background(MaterialTheme.colorScheme.primary)
                    .size(48.dp)
                    .clickable {
                        if (currentScreenState.playing) {
                            val intent = Intent(context, MediaService::class.java).apply {
                                action = "PAUSE"
                            }
                            context.startService(intent)
                        } else {
                            val intent = Intent(context, MediaService::class.java).apply {
                                action = "PLAY"
                            }
                            context.startService(intent)
                        }
                        viewModel.updatePlayingState()
                    }
            )
        }

        Button(onClick = {
            val intent = Intent(context, MediaService::class.java).apply {
                action = "STOP"
            }
            context.startService(intent)
        }) {
            Text("Stop Service")
        }
    }
}




