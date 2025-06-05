// di/AppModule.kt
package com.example.everynoiseatonce.di

import android.app.Application
import android.content.Context
import com.example.everynoiseatonce.utils.UserProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object AppModule {

    @Provides
    @Singleton
    fun provideContext(app: Application): Context = app

    @Provides
    @Singleton
    fun provideUserProvider(): UserProvider = UserProvider()
}

