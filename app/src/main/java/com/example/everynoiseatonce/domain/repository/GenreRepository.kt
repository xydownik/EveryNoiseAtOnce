package com.example.everynoiseatonce.domain.repository

import com.example.everynoiseatonce.domain.model.ArtistSearchResponse
import com.example.everynoiseatonce.domain.model.GenresResponse

interface GenreRepository {
    suspend fun getGenres(): GenresResponse?
    suspend fun searchArtistsByGenre(genre: String): ArtistSearchResponse?
}
