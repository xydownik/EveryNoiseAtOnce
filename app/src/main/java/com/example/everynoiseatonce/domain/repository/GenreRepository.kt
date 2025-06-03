package com.example.everynoiseatonce.domain.repository

import com.example.everynoiseatonce.domain.model.ArtistSearchResponse

interface GenreRepository {
    suspend fun searchArtistsByGenre(genre: String): ArtistSearchResponse?
}
