package com.example.everynoiseatonce.data.api

import com.example.everynoiseatonce.domain.model.ArtistSearchResponse
import com.example.everynoiseatonce.domain.model.RelatedArtistsResponse
import com.example.everynoiseatonce.domain.model.TopTracksResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface SpotifyApi {
    @GET("v1/search")
    suspend fun searchArtistsByGenre(
        @Header("Authorization") authHeader: String,
        @Query("q") query: String,
        @Query("type") type: String = "artist"
    ): ArtistSearchResponse

    @GET("v1/artists/{id}/top-tracks")
    suspend fun getTopTracks(
        @Header("Authorization") token: String?,
        @Path("id") artistId: String,
        @Query("market") market: String = "US"
    ): TopTracksResponse

    @GET("v1/artists/{id}/related-artists")
    suspend fun getRelatedArtists(
        @Header("Authorization") token: String?,
        @Path("id") artistId: String
    ): RelatedArtistsResponse
}
