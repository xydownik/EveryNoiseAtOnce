package com.example.everynoiseatonce.data.repository

import android.util.Base64
import android.util.Log
import android.util.Log.e
import android.util.Log.println
import com.example.everynoiseatonce.BuildConfig
import com.example.everynoiseatonce.data.api.SpotifyApi
import com.example.everynoiseatonce.data.api.SpotifyAuthApi
import com.example.everynoiseatonce.domain.model.Artist
import com.example.everynoiseatonce.domain.model.ArtistSearchResponse
import com.example.everynoiseatonce.domain.model.AuthTokenProvider
import com.example.everynoiseatonce.domain.model.Track
import com.example.everynoiseatonce.domain.repository.GenreRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Inject

class SpotifyRepository @Inject constructor(
    private val authApi: SpotifyAuthApi,
    private val spotifyApi: SpotifyApi
) : GenreRepository, AuthTokenProvider {

    private var token: String? = null

    private suspend fun ensureToken(): String? {
        if (token != null) return token?.let { "Bearer $it" }

        val credentials = "${BuildConfig.SPOTIFY_CLIENT_ID}:${BuildConfig.SPOTIFY_CLIENT_SECRET}"
        val basicAuth = "Basic " + Base64.encodeToString(credentials.toByteArray(), Base64.NO_WRAP)
        Log.d("SpotifyAuth", "Authorization header: $basicAuth")

        return withContext(Dispatchers.IO) {
            try {
                val response = authApi.getAccessToken(basicAuth)
                val errorBody = response.errorBody()?.string()
                e("SpotifyAuth", "Raw response error body: $errorBody")
                if (response.isSuccessful) {
                    val body = response.body()
                    Log.d("SpotifyAuth", "Access token response: $body")
                    val access = body?.accessToken
                    token = access
                    access?.let { "Bearer $it" }
                } else {
                    val error = response.errorBody()?.string()
                    e("SpotifyAuth", "Error getting token: ${response.code()} - $error")
                    null
                }
            } catch (e: Exception) {
                e("SpotifyAuth", "Exception during token fetch", e)
                null
            }
        }
    }
    override suspend fun getToken(): String? {
        return ensureToken()
    }

    override suspend fun searchArtistsByGenre(genre: String): ArtistSearchResponse? {
        val authHeader = ensureToken() ?: return null
        return withContext(Dispatchers.IO) {
            spotifyApi.searchArtistsByGenre(authHeader, "genre:$genre")
        }
    }
}
