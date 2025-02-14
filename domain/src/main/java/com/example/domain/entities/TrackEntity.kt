package com.example.domain.entities

data class TrackEntity(
    val artist: ArtistEntity,
    val albumEntity: AlbumEntity,
    val duration: Int,
    val id: Long,
    val preview: String,
    val title: String,
)

