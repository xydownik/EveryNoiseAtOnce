package com.example.everynoiseatonce.domain.usecase

import com.example.everynoiseatonce.domain.model.GenresResponse
import com.example.everynoiseatonce.domain.repository.GenreRepository
import javax.inject.Inject

class GetGenresUseCase @Inject constructor(
    private val repository: GenreRepository
) {
    suspend operator fun invoke(): GenresResponse? {
        return repository.getGenres()
    }
}
