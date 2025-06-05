package com.example.everynoiseatonce.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.everynoiseatonce.data.local.entity.ArtistEntity

@Dao
interface ArtistDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArtists(artists: List<ArtistEntity>)

    @Query("SELECT * FROM artists WHERE parentId = :parentArtistId")
    suspend fun getArtistsByParentArtistId(parentArtistId: String): List<ArtistEntity>

    @Query("DELETE FROM artists WHERE parentId = :parentArtistId")
    suspend fun deleteByParentArtistId(parentArtistId: String)
}

