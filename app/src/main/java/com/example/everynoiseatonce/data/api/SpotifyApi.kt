package com.example.everynoiseatonce.data.api

import com.example.everynoiseatonce.domain.model.ArtistSearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface SpotifyApi {
    // Поиск артистов по жанру (через поисковый запрос типа "genre:<genre>")
    @GET("v1/search")
    suspend fun searchArtistsByGenre(
        @Header("Authorization") authHeader: String,
        @Query("q") query: String,         // e.g., "genre:rock"
        @Query("type") type: String = "artist",
        @Query("limit") limit: Int = 20
    ): ArtistSearchResponse
}
