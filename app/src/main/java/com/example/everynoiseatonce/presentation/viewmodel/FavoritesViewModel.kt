package com.example.everynoiseatonce.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.everynoiseatonce.domain.model.Artist
import com.example.everynoiseatonce.domain.model.Genre
import com.example.everynoiseatonce.domain.repository.FavoritesRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavoritesViewModel @Inject constructor(
    private val favoritesRepository: FavoritesRepository
) : ViewModel() {

    private val _filter = MutableStateFlow(Filter.ALL)
    val filter: StateFlow<Filter> = _filter

    val favorites: StateFlow<List<Any>> = combine(
        favoritesRepository.getFavoriteGenresFlow(),
        favoritesRepository.getFavoriteArtistsFlow(),
        _filter
    ) { genres, artists, filter ->
        when (filter) {
            Filter.GENRES -> genres
            Filter.ARTISTS -> artists
            Filter.ALL -> genres + artists
        }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    fun setFilter(newFilter: Filter) {
        _filter.value = newFilter
    }

    fun toggleFavoriteArtist(artist: Artist) {
        viewModelScope.launch {
            favoritesRepository.toggleArtist(artist)
        }
    }

    fun toggleFavoriteGenre(genre: Genre) {
        viewModelScope.launch {
            favoritesRepository.toggleGenre(genre)
        }
    }

    enum class Filter { ALL, GENRES, ARTISTS }
}
