package com.example.everynoiseatonce.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.everynoiseatonce.data.local.entity.FavoriteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM favorites WHERE userId = :userId ORDER BY rowid DESC")
    fun getAllFavorites(userId: String): Flow<List<FavoriteEntity>>

    @Query("SELECT * FROM favorites WHERE type = :type AND userId = :userId ORDER BY rowid DESC")
    fun getFavoritesByType(type: String, userId: String): Flow<List<FavoriteEntity>>

    @Query("SELECT * FROM favorites WHERE id = :id AND userId = :userId LIMIT 1")
    suspend fun getById(id: String, userId: String): FavoriteEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: FavoriteEntity)

    @Query("DELETE FROM favorites WHERE id = :id AND userId = :userId")
    suspend fun deleteById(id: String, userId: String)
}
