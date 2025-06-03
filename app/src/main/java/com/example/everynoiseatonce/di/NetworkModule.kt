// di/NetworkModule.kt
package com.example.everynoiseatonce.di

import com.example.everynoiseatonce.data.api.SpotifyApi
import com.example.everynoiseatonce.data.api.SpotifyAuthApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
object NetworkModule {

    private const val BASE_URL_SPOTIFY = "https://api.spotify.com/"
    private const val BASE_URL_AUTH = "https://accounts.spotify.com/"

    @Provides
    @Singleton
    fun provideMoshi(): Moshi =
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory()) // <-- ВАЖНО!
            .build()

    @Provides
    @Singleton
    fun provideSpotifyApi(moshi: Moshi): SpotifyApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL_SPOTIFY)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(SpotifyApi::class.java)
    }

    @Provides
    @Singleton
    fun provideSpotifyAuthApi(moshi: Moshi): SpotifyAuthApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL_AUTH)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(SpotifyAuthApi::class.java)
    }
}
