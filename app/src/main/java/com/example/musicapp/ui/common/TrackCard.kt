package com.example.musicapp.ui.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.domain.entities.TrackEntity

@Composable
fun TrackCard(
    trackEntity: TrackEntity,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .clickable { onClick() }
            .height(48.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        AsyncImage(
            trackEntity.albumEntity.cover,
            contentDescription = null,
            modifier = Modifier.size(48.dp)
        )
        Column(verticalArrangement = Arrangement.SpaceAround, modifier = Modifier.fillMaxHeight()) {
            TextTrackName(trackEntity.title)
            TextArtistName(trackEntity.artist.name)
        }

    }
}
