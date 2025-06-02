package com.example.everynoiseatonce.domain.model

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
    val external_urls: ExternalUrls
)

data class Image(
    val url: String
)

data class ExternalUrls(
    val spotify: String
)
