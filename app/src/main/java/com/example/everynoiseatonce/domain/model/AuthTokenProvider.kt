package com.example.everynoiseatonce.domain.model

interface AuthTokenProvider {
    suspend fun getToken(): String?
}
