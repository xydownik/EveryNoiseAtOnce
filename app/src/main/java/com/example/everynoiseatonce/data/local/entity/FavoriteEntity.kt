package com.example.everynoiseatonce.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.everynoiseatonce.domain.model.ExternalUrls

@Entity(tableName = "favorites", primaryKeys = ["id", "userId"])
data class FavoriteEntity(
    val id: String,
    val name: String,
    val type: String,
    val imageUrl: String? = null,
    val externalUrl: String? = null,
    val userId: String
)

