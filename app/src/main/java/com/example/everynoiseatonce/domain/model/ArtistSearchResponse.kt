package com.example.everynoiseatonce.domain.model

import java.io.Serializable


data class ArtistSearchResponse(
    val artists: Artists
)

data class Artists(
    val items: List<Artist>
)


data class Artist(
    val id: String,
    val name: String,
    val images: List<Image>?,
    val external_urls: ExternalUrls,
    var isFavorite: Boolean = false
): Serializable

data class Image(
    val url: String
): Serializable

data class ExternalUrls(
    val spotify: String
): Serializable
