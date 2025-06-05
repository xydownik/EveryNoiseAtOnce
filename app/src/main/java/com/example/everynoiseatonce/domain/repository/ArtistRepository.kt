package com.example.everynoiseatonce.domain.repository

import com.example.everynoiseatonce.domain.model.Artist
import com.example.everynoiseatonce.domain.model.Track

interface ArtistRepository {
    suspend fun getTopTracks(artistId: String): List<Track>
    suspend fun getRelatedArtists(artistId: String): List<Artist>
}
