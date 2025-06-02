package com.example.everynoiseatonce.di

import com.example.everynoiseatonce.data.repository.SpotifyRepository
import com.example.everynoiseatonce.domain.repository.GenreRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindGenreRepository(
        spotifyRepository: SpotifyRepository
    ): GenreRepository
}
