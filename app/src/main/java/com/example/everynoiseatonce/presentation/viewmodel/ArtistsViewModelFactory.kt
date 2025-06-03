package com.example.everynoiseatonce.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.everynoiseatonce.data.api.SpotifyApi
import com.example.everynoiseatonce.data.repository.SpotifyRepository
import javax.inject.Inject


class ArtistsViewModelFactory @Inject constructor(
    private val repository: SpotifyRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ArtistsViewModel(repository) as T
    }
}