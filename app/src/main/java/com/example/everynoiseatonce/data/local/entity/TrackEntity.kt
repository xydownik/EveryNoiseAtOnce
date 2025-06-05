package com.example.everynoiseatonce.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.everynoiseatonce.domain.model.Album
import com.example.everynoiseatonce.domain.model.ExternalUrls
import com.example.everynoiseatonce.domain.model.Image
import com.example.everynoiseatonce.domain.model.Track

@Entity(tableName = "tracks")
data class TrackEntity(
    @PrimaryKey val id: String,
    val name: String,
    val previewUrl: String?,
    val imageUrl: String?,
    val spotifyUrl: String,
    val artistId: String

){
    companion object {
        fun from(track: Track, artistId: String): TrackEntity {
            return TrackEntity(
                id = track.id,
                name = track.name,
                previewUrl = track.preview_url,
                imageUrl = track.album.images.firstOrNull()?.url,
                spotifyUrl = track.external_urls.spotify,
                artistId = artistId
            )
        }
    }

    fun toTrack(): Track {
        return Track(
            id = id,
            name = name,
            preview_url = previewUrl,
            external_urls = ExternalUrls(spotifyUrl),
            album = Album(name = "", images = imageUrl?.let { listOf(Image(it)) } ?: emptyList()),
            artists = emptyList()
        )
    }
}
