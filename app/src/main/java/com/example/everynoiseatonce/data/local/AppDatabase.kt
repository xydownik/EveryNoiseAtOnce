package com.example.everynoiseatonce.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.everynoiseatonce.data.local.dao.ArtistDao
import com.example.everynoiseatonce.data.local.dao.TrackDao
import com.example.everynoiseatonce.data.local.entity.ArtistEntity
import com.example.everynoiseatonce.data.local.entity.TrackEntity

@Database(entities = [ArtistEntity::class, TrackEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun artistDao(): ArtistDao
    abstract fun trackDao(): TrackDao
}
