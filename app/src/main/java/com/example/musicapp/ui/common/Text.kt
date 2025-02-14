package com.example.musicapp.ui.common

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp

@Composable
fun TextArtistName(text: String) {
    Text(
        color = MaterialTheme.colorScheme.secondary,
        text = text,
        fontSize = 12.sp
    )
}

@Composable
fun TextTrackName(text: String) {
    Text(
        color = MaterialTheme.colorScheme.primary,
        text = text,
        fontSize = 16.sp,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}
