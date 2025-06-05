package com.example.everynoiseatonce.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.everynoiseatonce.domain.model.Genre
import com.example.everynoiseatonce.domain.repository.FavoritesRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class GenresViewModel @Inject constructor(
    private val favoritesRepository: FavoritesRepository
) : ViewModel() {

    private val _allGenres = MutableStateFlow<List<Genre>>(emptyList())
    val allGenres: StateFlow<List<Genre>> = _allGenres

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query

    val filteredGenres: StateFlow<List<Genre>> = combine(_allGenres, _query) { genres, q ->
        if (q.isBlank()) genres
        else genres.filter { it.name.contains(q, ignoreCase = true) }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    fun setGenres(genres: List<Genre>) {
        viewModelScope.launch {
            val favorites = favoritesRepository.getFavoriteGenresFlow().first()
            _allGenres.value = genres.map { genre ->
                genre.copy(isFavorite = favorites.any { it.name == genre.name })
            }
        }
    }

    fun onQueryChanged(newQuery: String) {
        _query.value = newQuery
    }

    fun toggleFavoriteGenre(genre: Genre) {
        viewModelScope.launch {
            favoritesRepository.toggleGenre(genre)
            _allGenres.value = _allGenres.value.map {
                if (it.name == genre.name) it.copy(isFavorite = !it.isFavorite) else it
            }
        }
    }
}
