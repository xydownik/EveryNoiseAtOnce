package com.example.everynoiseatonce.domain.model

data class Track(
    val id: String,
    val name: String,
    val preview_url: String?,
    val external_urls: ExternalUrls,
    val album: Album,
    val artists: List<Artist>
)

data class Album(
    val name: String,
    val images: List<Image>
)
