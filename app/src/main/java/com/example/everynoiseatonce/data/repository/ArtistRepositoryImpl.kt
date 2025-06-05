package com.example.everynoiseatonce.data.repository

import android.util.Log
import com.example.everynoiseatonce.data.api.SpotifyApi
import com.example.everynoiseatonce.data.api.SpotifyAuthApi
import com.example.everynoiseatonce.data.local.dao.ArtistDao
import com.example.everynoiseatonce.data.local.dao.TrackDao
import com.example.everynoiseatonce.data.local.entity.ArtistEntity
import com.example.everynoiseatonce.data.local.entity.TrackEntity
import com.example.everynoiseatonce.domain.model.Artist
import com.example.everynoiseatonce.domain.model.Track
import com.example.everynoiseatonce.domain.repository.ArtistRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ArtistRepositoryImpl @Inject constructor(
    private val spotifyApi: SpotifyApi,
    private val authProvider: SpotifyRepository,
    private val artistDao: ArtistDao,
    private val trackDao: TrackDao
) : ArtistRepository {

    override suspend fun getTopTracks(artistId: String): List<Track> = withContext(Dispatchers.IO) {
        try {
            val response = spotifyApi.getTopTracks(authProvider.getToken(), artistId)
            val tracks = response.tracks

            // Save to Room
            trackDao.deleteTracksByArtistId(artistId)
            trackDao.insertAll(tracks.map { TrackEntity.from(it, artistId) })

            tracks
        } catch (e: Exception) {
            trackDao.getTracksByArtistId(artistId).map { it.toTrack() }
        }
    }

    override suspend fun getRelatedArtists(artistId: String): List<Artist> = withContext(Dispatchers.IO) {
        try {
            val token = authProvider.getToken()
            Log.d("ArtistRepository", "Calling related-artists with token=$token artistId=$artistId")
            val response = spotifyApi.getRelatedArtists(token, artistId)
            val artists = response.artists

            Log.d("ArtistRepository", "Related artists loaded: ${artists.size}")

            artistDao.deleteByParentArtistId(artistId)
            artistDao.insertArtists(artists.map { ArtistEntity.from(it, artistId) })

            artists
        } catch (e: Exception) {
            Log.e("ArtistRepository", "Failed to fetch related artists from API, loading from cache", e)

            artistDao.getArtistsByParentArtistId(artistId).map { it.toArtist() }
        }
    }

}
