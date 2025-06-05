package com.example.everynoiseatonce.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.example.everynoiseatonce.data.local.dao.FavoriteDao
import com.example.everynoiseatonce.data.local.entity.FavoriteEntity
import com.example.everynoiseatonce.domain.model.Artist
import com.example.everynoiseatonce.domain.model.ExternalUrls
import com.example.everynoiseatonce.domain.model.Genre
import com.example.everynoiseatonce.domain.model.Image
import com.example.everynoiseatonce.domain.repository.FavoritesRepository
import com.example.everynoiseatonce.utils.UserProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavoritesRepositoryImpl @Inject constructor(
    private val dao: FavoriteDao,
    private val userProvider: UserProvider
) : FavoritesRepository {

    override fun getFavoriteGenresFlow(): Flow<List<Genre>> {
        val userId = userProvider.getUserId()
        return dao.getFavoritesByType("genre", userId).map { list ->
            list.map { Genre(name = it.name, isFavorite = true) }
        }
    }

    override fun getFavoriteArtistsFlow(): Flow<List<Artist>> {
        val userId = userProvider.getUserId()
        return dao.getFavoritesByType("artist", userId).map { list ->
            list.map {
                Artist(
                    id = it.id,
                    name = it.name,
                    images = it.imageUrl?.let { url -> listOf(Image(url)) },
                    external_urls = ExternalUrls(it.externalUrl ?: ""),
                    isFavorite = true
                )
            }
        }
    }

    override fun getAllFavorites(): LiveData<List<Any>> {
        val userId = userProvider.getUserId()
        return dao.getAllFavorites(userId).map { list ->
            list.map {
                when (it.type) {
                    "artist" -> Artist(
                        id = it.id,
                        name = it.name,
                        images = it.imageUrl?.let { url -> listOf(Image(url)) },
                        external_urls = ExternalUrls(it.externalUrl ?: ""),
                        isFavorite = true
                    )
                    "genre" -> Genre(name = it.name, isFavorite = true)
                    else -> throw IllegalArgumentException("Unknown type ${it.type}")
                }
            }
        }.asLiveData()
    }

    override suspend fun toggleGenre(genre: Genre) {
        val userId = userProvider.getUserId()
        val existing = dao.getById(genre.name, userId)
        if (existing != null) {
            dao.deleteById(genre.name, userId)
        } else {
            dao.insertFavorite(
                FavoriteEntity(
                    id = genre.name,
                    name = genre.name,
                    type = "genre",
                    userId = userId
                )
            )
        }
    }

    override suspend fun toggleArtist(artist: Artist) {
        val userId = userProvider.getUserId()
        val existing = dao.getById(artist.id, userId)
        if (existing != null) {
            dao.deleteById(artist.id, userId)
        } else {
            dao.insertFavorite(
                FavoriteEntity(
                    id = artist.id,
                    name = artist.name,
                    type = "artist",
                    imageUrl = artist.images?.firstOrNull()?.url,
                    externalUrl = artist.external_urls?.spotify,
                    userId = userId
                )
            )
        }
    }

    override fun isGenreFavorite(genre: Genre): Boolean = false
    override fun isArtistFavorite(artist: Artist): Boolean = false
}
