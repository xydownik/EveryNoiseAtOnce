package com.example.everynoiseatonce.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.everynoiseatonce.data.api.SpotifyApi
import com.example.everynoiseatonce.data.repository.SpotifyRepository
import com.example.everynoiseatonce.domain.model.Artist
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ArtistsViewModel(
    private val repository: SpotifyRepository
) : ViewModel() {

    private val _artists = MutableStateFlow<List<Artist>>(emptyList())
    val artists: StateFlow<List<Artist>> = _artists

    fun loadArtists(genre: String) {
        viewModelScope.launch {
            try {
                val result = repository.searchArtistsByGenre(genre)
                _artists.value = result?.artists?.items ?: emptyList()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}




