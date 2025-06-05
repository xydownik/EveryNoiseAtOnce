package com.example.everynoiseatonce.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.everynoiseatonce.data.local.entity.TrackEntity

@Dao
interface TrackDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(tracks: List<TrackEntity>)

    @Query("SELECT * FROM tracks WHERE artistId = :artistId")
    suspend fun getTracksByArtistId(artistId: String): List<TrackEntity>

    @Query("DELETE FROM tracks WHERE artistId = :artistId")
    suspend fun deleteTracksByArtistId(artistId: String)
}

