// EveryNoiseApp.kt
package com.example.everynoiseatonce

import android.app.Application
import com.example.everynoiseatonce.di.AppComponent
import com.example.everynoiseatonce.di.DaggerAppComponent

class EveryNoiseApp : Application() {

    lateinit var appComponent: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.factory().create(this)
    }
}

