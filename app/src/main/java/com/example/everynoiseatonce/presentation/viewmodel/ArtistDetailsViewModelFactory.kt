package com.example.everynoiseatonce.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.everynoiseatonce.domain.repository.ArtistRepository

class ArtistDetailsViewModelFactory(
    private val repository: ArtistRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ArtistDetailsViewModel::class.java)) {
            return ArtistDetailsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
