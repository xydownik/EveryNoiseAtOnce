package com.example.everynoiseatonce.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.everynoiseatonce.domain.model.Artist
import com.example.everynoiseatonce.data.repository.SpotifyRepository
import com.example.everynoiseatonce.domain.repository.FavoritesRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class ArtistsViewModel @Inject constructor(
    private val repository: SpotifyRepository,
    private val favoritesRepository: FavoritesRepository
) : ViewModel() {

    private val _artists = MutableStateFlow<List<Artist>>(emptyList())
    val artists: StateFlow<List<Artist>> = _artists

    fun loadArtists(genre: String) {
        viewModelScope.launch {
            try {
                val result = repository.searchArtistsByGenre(genre)?.artists?.items ?: emptyList()
                val favoriteIds = favoritesRepository.getFavoriteArtistsFlow().first().map { it.id }
                _artists.value = result.map { it.copy(isFavorite = it.id in favoriteIds) }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun toggleFavoriteArtist(artist: Artist) {
        viewModelScope.launch {
            favoritesRepository.toggleArtist(artist)
            _artists.value = _artists.value.map {
                if (it.id == artist.id) it.copy(isFavorite = !it.isFavorite) else it
            }
        }
    }
}
