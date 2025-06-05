// di/DatabaseModule.kt
package com.example.everynoiseatonce.di

import android.content.Context
import androidx.room.Room
import com.example.everynoiseatonce.data.local.AppDatabase
import com.example.everynoiseatonce.data.local.dao.ArtistDao
import com.example.everynoiseatonce.data.local.dao.FavoriteDao
import com.example.everynoiseatonce.data.local.dao.TrackDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "every_noise_db"
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideArtistDao(db: AppDatabase): ArtistDao = db.artistDao()

    @Provides
    fun provideTrackDao(db: AppDatabase): TrackDao = db.trackDao()

    @Provides
    fun provideFavoriteDao(db: AppDatabase): FavoriteDao = db.favoritesDao()
}
