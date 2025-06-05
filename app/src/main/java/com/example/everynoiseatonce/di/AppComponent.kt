// di/AppComponent.kt
package com.example.everynoiseatonce.di

import android.app.Application
import android.content.Context
import com.example.everynoiseatonce.domain.repository.ArtistRepository
import com.example.everynoiseatonce.domain.repository.FavoritesRepository
import com.example.everynoiseatonce.presentation.activity.MainActivity
import com.example.everynoiseatonce.presentation.ui.ArtistsFragment
import com.example.everynoiseatonce.presentation.ui.FavoritesFragment
import com.example.everynoiseatonce.presentation.ui.GenresFragment
import com.example.everynoiseatonce.presentation.ui.ProfileFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        NetworkModule::class,
        RepositoryModule::class,
        DatabaseModule::class,
        FavoritesModule::class
    ]
)
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance app: Application
        ): AppComponent
    }
    fun artistRepository(): ArtistRepository
    fun favoritesRepository(): FavoritesRepository
    fun inject(activity: MainActivity)
    fun inject(fragment: GenresFragment)
    fun inject(fragment: ArtistsFragment)
    fun inject(fragment: FavoritesFragment)
    fun inject(fragment: ProfileFragment)
}

