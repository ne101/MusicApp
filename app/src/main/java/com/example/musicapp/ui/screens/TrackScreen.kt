package com.example.musicapp.ui.screens


import android.content.ComponentName
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.media3.common.Player
import androidx.media3.common.Timeline
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import coil.compose.AsyncImage
import com.example.musicapp.R
import com.example.musicapp.app.getApplicationComponent
import com.example.musicapp.screen_states.TrackScreenState
import com.example.musicapp.service.MediaService
import com.example.musicapp.ui.common.ErrorScreen
import com.example.musicapp.ui.common.InitialScreen
import com.example.musicapp.ui.common.TextArtistName
import com.example.musicapp.ui.common.TextTrackName
import com.example.musicapp.viewmodels.TrackViewModel
import kotlinx.coroutines.guava.await

@Composable
fun TrackScreen(id: Long, paddingValues: PaddingValues) {
    val component = getApplicationComponent().getTrackScreenComponentFactory().create(id)
    val viewModel: TrackViewModel = viewModel(factory = component.getViewModelFactory())
    val screenState = viewModel.getScreenState().collectAsStateWithLifecycle()
    ScreenState(
     screenState, paddingValues, viewModel
    )

}
@Composable
private fun ScreenState(
    screenState: State<TrackScreenState>,
    paddingValues: PaddingValues,
    viewModel: TrackViewModel,
) {
    when (val currentScreenState = screenState.value) {
        is TrackScreenState.MainContent -> {
            MediaPlayer(currentScreenState, paddingValues, viewModel)
        }

        is TrackScreenState.Error -> {
            ErrorScreen(currentScreenState.message)
        }

        TrackScreenState.Initial -> {
            InitialScreen()
        }
    }
}


@Composable
private fun MediaPlayer(
    currentScreenState: TrackScreenState.MainContent,
    paddingValues: PaddingValues,
    viewModel: TrackViewModel,
) {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        context.startService(
            Intent(context, MediaService::class.java).apply {
                putExtra("MUSIC_URL", currentScreenState.trackEntity.preview)
            }
        )
    }
    val mediaControllerDeferred = remember {
        MediaController.Builder(
            context,
            SessionToken(context, ComponentName(context, MediaService::class.java))
        ).buildAsync()
    }

    var mediaController by remember { mutableStateOf<MediaController?>(null) }

    LaunchedEffect(mediaControllerDeferred) {
        mediaController = mediaControllerDeferred.await()
    }



    LaunchedEffect(mediaController) {
        val listener = object : Player.Listener {
            override fun onPlaybackStateChanged(playbackState: Int) {
                when (playbackState) {
                    Player.STATE_READY -> viewModel.updatePlayingState(
                        mediaController?.isPlaying ?: false
                    )

                    Player.STATE_ENDED -> viewModel.updatePlayingState(false)
                }
            }

            override fun onIsPlayingChanged(isPlaying: Boolean) {
                viewModel.updatePlayingState(isPlaying)
                if (isPlaying && mediaController != null) {
                    viewModel.startPositionUpdate(mediaController!!)

                } else {
                    viewModel.stopPositionUpdate()
                }
            }

            override fun onTimelineChanged(timeline: Timeline, reason: Int) {
                viewModel.updateDuration(mediaController?.duration ?: 0L)
            }

        }
        mediaController?.addListener(listener)
    }

    if (mediaController == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(paddingValues),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
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
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "${formatTime(currentScreenState.playbackPosition)} / ${
                    formatTime(
                        currentScreenState.duration
                    )
                }"
            )
            Spacer(modifier = Modifier.height(16.dp))
            Slider(
                value = currentScreenState.playbackPosition.toFloat(),
                onValueChange = { newPos ->
                    viewModel.updatePlaybackPosition(newPos.toLong())
                },
                onValueChangeFinished = {
                    mediaController?.seekTo(currentScreenState.playbackPosition)
                },
                valueRange = 0f..currentScreenState.duration.coerceAtLeast(0L)
                    .toFloat(),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))
            val image = if (currentScreenState.playing) {
                R.drawable.pause_48dp_e8eaed_fill0_wght400_grad0_opsz48
            } else {
                R.drawable.play_arrow_48dp_e8eaed_fill0_wght400_grad0_opsz48
            }
            Icon(
                imageVector = ImageVector.vectorResource(image),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .clip(RoundedCornerShape(32.dp))
                    .background(MaterialTheme.colorScheme.primary)
                    .size(48.dp)
                    .clickable {
                        if (currentScreenState.playing) {
                            mediaController?.pause()
                        } else {
                            mediaController?.play()
                        }
                    }
            )
        }
    }
}

fun formatTime(ms: Long): String {
    val totalSeconds = ms / 1000
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    return "%02d:%02d".format(minutes, seconds)
}

