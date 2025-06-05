package com.example.everynoiseatonce.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.everynoiseatonce.domain.model.Artist
import com.example.everynoiseatonce.domain.model.Track
import com.example.everynoiseatonce.domain.repository.ArtistRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class ArtistDetailsViewModel(
    private val repository: ArtistRepository
) : ViewModel() {

    private val _topTracks = MutableStateFlow<List<Track>>(emptyList())
    val topTracks: StateFlow<List<Track>> = _topTracks

    private val _relatedArtists = MutableStateFlow<List<Artist>>(emptyList())
    val relatedArtists: StateFlow<List<Artist>> = _relatedArtists

    fun loadArtistDetails(artistId: String) {
        viewModelScope.launch {
            val tracks = repository.getTopTracks(artistId)
            val artists = repository.getRelatedArtists(artistId)
            Log.d("ArtistDetailsVM", "Loading artist ID: $artistId")
            Log.d("ArtistDetailsVM", "Tracks loaded: ${tracks.size}, Related: ${artists.size}")
            _topTracks.value = tracks
            _relatedArtists.value = artists
        }
    }
}


