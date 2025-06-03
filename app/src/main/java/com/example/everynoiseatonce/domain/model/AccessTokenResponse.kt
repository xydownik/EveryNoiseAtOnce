package com.example.everynoiseatonce.domain.model

import com.squareup.moshi.Json

data class AccessTokenResponse(
    @Json(name = "access_token") val accessToken: String?,
    @Json(name = "token_type") val tokenType: String?,
    @Json(name = "expires_in") val expiresIn: Int?
)
