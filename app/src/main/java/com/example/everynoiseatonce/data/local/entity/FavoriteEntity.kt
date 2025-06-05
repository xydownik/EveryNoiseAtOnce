package com.example.everynoiseatonce.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class FavoriteEntity(
    @PrimaryKey val id: String,
    val name: String,
    val imageUrl: String?,
    val type: String // "genre" or "artist"
)
