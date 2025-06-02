// di/AppComponent.kt
package com.example.everynoiseatonce.di

import android.app.Application
import com.example.everynoiseatonce.presentation.activity.MainActivity
import com.example.everynoiseatonce.presentation.ui.FavoritesFragment
import com.example.everynoiseatonce.presentation.ui.GenresFragment
import com.example.everynoiseatonce.presentation.ui.ProfileFragment
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

    fun inject(fragment: GenresFragment)
    fun inject(activity: MainActivity)
    fun inject(fragment: ProfileFragment)
    fun inject(fragment: FavoritesFragment)
}
