package com.example.everynoiseatonce.di

import android.content.Context
import android.content.SharedPreferences
import com.example.everynoiseatonce.data.local.dao.FavoriteDao
import com.example.everynoiseatonce.data.repository.FavoritesRepositoryImpl
import com.example.everynoiseatonce.domain.repository.FavoritesRepository
import com.example.everynoiseatonce.utils.UserProvider
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object FavoritesModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences("favorites_prefs", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideFavoritesRepository(
        favoriteDao: FavoriteDao,
        userProvider: UserProvider
    ): FavoritesRepository {
        return FavoritesRepositoryImpl(favoriteDao, userProvider)
    }

}

