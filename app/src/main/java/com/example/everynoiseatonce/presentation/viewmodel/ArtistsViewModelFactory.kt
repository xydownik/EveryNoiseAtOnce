package com.example.everynoiseatonce.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.everynoiseatonce.data.api.SpotifyApi
import com.example.everynoiseatonce.data.repository.SpotifyRepository
import com.example.everynoiseatonce.domain.repository.FavoritesRepository
import javax.inject.Inject


class ArtistsViewModelFactory @Inject constructor(
    private val repository: SpotifyRepository,
    private val favoritesRepository: FavoritesRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ArtistsViewModel(repository, favoritesRepository) as T
    }
}