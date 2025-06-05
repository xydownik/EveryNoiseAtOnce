package com.example.everynoiseatonce.di

import android.content.Context
import com.example.everynoiseatonce.utils.ExoPlayerHolder
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ServiceModule {
    @Provides
    @Singleton
    fun provideExoPlayerHolder(context: Context): ExoPlayerHolder {
        return ExoPlayerHolder(context)
    }
}
