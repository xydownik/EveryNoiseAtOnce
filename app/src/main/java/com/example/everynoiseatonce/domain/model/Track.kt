package com.example.everynoiseatonce.domain.model

import java.io.Serializable


data class Track(
    val id: String,
    val name: String,
    val preview_url: String? = "android.resource://com.example.everynoiseatonce/raw/sample_track",
    val external_urls: ExternalUrls,
    val album: Album,
    val artists: List<Artist>
): Serializable


data class Album(
    val name: String,
    val images: List<Image>
): Serializable
