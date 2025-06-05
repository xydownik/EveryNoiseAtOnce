package com.example.everynoiseatonce.domain.repository

import androidx.lifecycle.LiveData
import com.example.everynoiseatonce.domain.model.Artist
import com.example.everynoiseatonce.domain.model.Genre
import kotlinx.coroutines.flow.Flow

interface FavoritesRepository {
    fun getFavoriteGenresFlow(): Flow<List<Genre>>
    fun getFavoriteArtistsFlow(): Flow<List<Artist>>
    fun getAllFavorites(): LiveData<List<Any>>

    suspend fun toggleGenre(genre: Genre)
    suspend fun toggleArtist(artist: Artist)

    fun isGenreFavorite(genre: Genre): Boolean
    fun isArtistFavorite(artist: Artist): Boolean
}
