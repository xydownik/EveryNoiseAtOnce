package com.example.everynoiseatonce.di

import android.content.Context
import com.example.everynoiseatonce.service.PlayerService
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ServiceModule::class])
interface ServiceComponent {

    fun inject(service: PlayerService)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): ServiceComponent
    }
}
