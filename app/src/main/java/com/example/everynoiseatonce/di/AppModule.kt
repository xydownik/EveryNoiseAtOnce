// di/AppModule.kt
package com.example.everynoiseatonce.di

import android.content.Context
import com.example.everynoiseatonce.EveryNoiseApp
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object AppModule {

    @Provides
    @Singleton
    fun provideContext(app: EveryNoiseApp): Context = app.applicationContext
}
