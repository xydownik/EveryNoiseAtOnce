package com.example.everynoiseatonce.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.everynoiseatonce.domain.model.Artist
import com.example.everynoiseatonce.domain.model.ExternalUrls
import com.example.everynoiseatonce.domain.model.Image

@Entity(tableName = "artists")
data class ArtistEntity(
    @PrimaryKey val id: String,
    val name: String,
    val imageUrl: String?,
    val spotifyUrl: String,
    val parentId: String? = null
){
    companion object {
        fun from(artist: Artist, parentId: String): ArtistEntity {
            return ArtistEntity(
                id = artist.id,
                name = artist.name,
                imageUrl = artist.images?.firstOrNull()?.url,
                spotifyUrl = artist.external_urls.spotify,
                parentId = parentId
            )
        }
    }

    fun toArtist(): Artist {
        return Artist(
            id = id,
            name = name,
            images = imageUrl?.let { listOf(Image(it)) },
            external_urls = ExternalUrls(spotifyUrl)
        )
    }
}

