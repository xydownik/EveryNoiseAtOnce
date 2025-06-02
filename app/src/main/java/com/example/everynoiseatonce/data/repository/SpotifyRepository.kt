package com.example.everynoiseatonce.data.repository

import android.util.Base64
import com.example.everynoiseatonce.BuildConfig
import com.example.everynoiseatonce.data.api.SpotifyApi
import com.example.everynoiseatonce.data.api.SpotifyAuthApi
import com.example.everynoiseatonce.domain.model.ArtistSearchResponse
import com.example.everynoiseatonce.domain.model.GenresResponse
import com.example.everynoiseatonce.domain.repository.GenreRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Inject

class SpotifyRepository @Inject constructor(
    private val authApi: SpotifyAuthApi,
    private val spotifyApi: SpotifyApi
) : GenreRepository {

    private var token: String? = null

    private suspend fun ensureToken(): String? {
        if (token != null) return token?.let { "Bearer $it" }

        val credentials = "${BuildConfig.SPOTIFY_CLIENT_ID}:${BuildConfig.SPOTIFY_CLIENT_SECRET}"
        val basicAuth = "Basic " + Base64.encodeToString(credentials.toByteArray(), Base64.NO_WRAP)

        return withContext(Dispatchers.IO) {
            val response = authApi.getAccessToken(basicAuth)
            val access = response.body()?.accessToken
            token = access
            access?.let { "Bearer $it" }
        }
    }

    override suspend fun getGenres(): GenresResponse? {
        val authHeader = ensureToken() ?: return null
        return withContext(Dispatchers.IO) {
            spotifyApi.getAvailableGenres(authHeader)
        }
    }

    override suspend fun searchArtistsByGenre(genre: String): ArtistSearchResponse? {
        val authHeader = ensureToken() ?: return null
        return withContext(Dispatchers.IO) {
            spotifyApi.searchArtistsByGenre(authHeader, "genre:$genre")
        }
    }
}
