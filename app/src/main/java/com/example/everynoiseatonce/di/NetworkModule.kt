// di/NetworkModule.kt
package com.example.everynoiseatonce.di

import com.example.everynoiseatonce.data.api.SpotifyApi
import com.example.everynoiseatonce.data.api.SpotifyAuthApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object NetworkModule {

    private const val BASE_URL = "https://api.spotify.com/v1/"

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder().build()

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(client)
            .build()

    @Provides
    @Singleton
    fun provideSpotifyApi(retrofit: Retrofit): SpotifyApi =
        retrofit.create(SpotifyApi::class.java)

    @Provides
    @Singleton
    fun provideSpotifyAuthApi(): SpotifyAuthApi {
        return Retrofit.Builder()
            .baseUrl("https://accounts.spotify.com/api/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(SpotifyAuthApi::class.java)
    }

}
