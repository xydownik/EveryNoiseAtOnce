package com.example.everynoiseatonce.data.repository

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.everynoiseatonce.domain.model.Artist
import com.example.everynoiseatonce.domain.model.Genre
import com.example.everynoiseatonce.domain.repository.FavoritesRepository
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

class FavoritesRepositoryImpl @Inject constructor(
    private val prefs: SharedPreferences,
    moshi: Moshi
) : FavoritesRepository {

    private val genreType = Types.newParameterizedType(List::class.java, Genre::class.java)
    private val artistType = Types.newParameterizedType(List::class.java, Artist::class.java)
    private val genreAdapter = moshi.adapter<List<Genre>>(genreType)
    private val artistAdapter = moshi.adapter<List<Artist>>(artistType)

    private val _favoriteGenres = MutableStateFlow(loadGenres())
    private val _favoriteArtists = MutableStateFlow(loadArtists())

    override fun getFavoriteGenresFlow(): StateFlow<List<Genre>> = _favoriteGenres
    override fun getFavoriteArtistsFlow(): StateFlow<List<Artist>> = _favoriteArtists
    override fun getAllFavorites(): LiveData<List<Any>> = MutableLiveData((_favoriteGenres.value ?: emptyList()) + (_favoriteArtists.value ?: emptyList()))

    override fun toggleGenre(genre: Genre) {
        val current = _favoriteGenres.value.toMutableList() ?: mutableListOf()
        if (current.any { it.name == genre.name }) {
            current.removeAll { it.name == genre.name }
        } else {
            current.add(genre)
        }
        _favoriteGenres.value = current
        saveGenres(current)
    }

    override fun toggleArtist(artist: Artist) {
        val current = _favoriteArtists.value?.toMutableList() ?: mutableListOf()
        if (current.any { it.id == artist.id }) {
            current.removeAll { it.id == artist.id }
        } else {
            current.add(artist)
        }
        _favoriteArtists.value = current
        saveArtists(current)
    }

    override fun isGenreFavorite(genre: Genre): Boolean {
        return _favoriteGenres.value.any { it.name == genre.name }
    }

    override fun isArtistFavorite(artist: Artist): Boolean {
        return _favoriteArtists.value.any { it.id == artist.id }
    }

    private fun saveGenres(genres: List<Genre>) {
        prefs.edit().putString("favorite_genres", genreAdapter.toJson(genres)).apply()
    }

    private fun saveArtists(artists: List<Artist>) {
        prefs.edit().putString("favorite_artists", artistAdapter.toJson(artists)).apply()
    }

    private fun loadGenres(): List<Genre> {
        val json = prefs.getString("favorite_genres", null) ?: return emptyList()
        return genreAdapter.fromJson(json) ?: emptyList()
    }

    private fun loadArtists(): List<Artist> {
        val json = prefs.getString("favorite_artists", null) ?: return emptyList()
        return artistAdapter.fromJson(json) ?: emptyList()
    }
}