package com.example.everynoiseatonce.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.everynoiseatonce.data.local.dao.ArtistDao
import com.example.everynoiseatonce.data.local.dao.FavoriteDao
import com.example.everynoiseatonce.data.local.dao.TrackDao
import com.example.everynoiseatonce.data.local.entity.ArtistEntity
import com.example.everynoiseatonce.data.local.entity.FavoriteEntity
import com.example.everynoiseatonce.data.local.entity.TrackEntity

@Database(entities = [ArtistEntity::class, TrackEntity::class, FavoriteEntity::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun artistDao(): ArtistDao
    abstract fun trackDao(): TrackDao
    abstract fun favoritesDao(): FavoriteDao
}
