package com.example.everynoiseatonce.domain.usecase
import com.example.everynoiseatonce.domain.repository.GenreRepository
import javax.inject.Inject

class GetGenresUseCase @Inject constructor(
    private val repository: GenreRepository // а не SpotifyRepository
)


