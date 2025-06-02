// di/AppComponent.kt
package com.example.everynoiseatonce.di

import android.app.Application
import com.example.everynoiseatonce.presentation.ui.MainFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, NetworkModule::class, RepositoryModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance app: Application): AppComponent
    }

    fun inject(fragment: MainFragment)
}
