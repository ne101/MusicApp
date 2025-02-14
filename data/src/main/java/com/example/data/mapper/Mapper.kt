package com.example.data.mapper

import com.example.data.models.Album
import com.example.data.models.Artist
import com.example.data.models.TrackResponse
import com.example.data.models.TracksResponse
import com.example.domain.entities.AlbumEntity
import com.example.domain.entities.ArtistEntity
import com.example.domain.entities.TrackEntity
import com.example.domain.entities.TracksEntity

object Mapper {

    private fun Artist.toDomain(): ArtistEntity = ArtistEntity(
        id = id,
        name = name
    )

    private fun Album.toDomain(): AlbumEntity = AlbumEntity(
        id = id,
        cover = cover
    )
    fun TrackResponse.toDomain(): TrackEntity = TrackEntity(
        artist = artist.toDomain(),
        albumEntity = album.toDomain(),
        duration = duration,
        id = id,
        preview = preview,
        title = title
    )

    fun TracksResponse.toDomain(): TracksEntity = TracksEntity(
        trackList = tracks.trackList.map { it.toDomain() }
    )
}