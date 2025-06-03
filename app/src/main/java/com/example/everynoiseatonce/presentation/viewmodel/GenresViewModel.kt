package com.example.everynoiseatonce.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.everynoiseatonce.domain.model.Genre
import com.example.everynoiseatonce.domain.usecase.GetGenresUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

class GenresViewModel : ViewModel() {

    private val _allGenres = MutableStateFlow<List<Genre>>(emptyList())
    val allGenres: StateFlow<List<Genre>> = _allGenres

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query

    val filteredGenres: StateFlow<List<Genre>> = combine(_allGenres, _query) { genres, q ->
        if (q.isBlank()) genres
        else genres.filter { it.name.contains(q, ignoreCase = true) }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    fun setGenres(genres: List<Genre>) {
        _allGenres.value = genres
    }

    fun onQueryChanged(newQuery: String) {
        _query.value = newQuery
    }
}

