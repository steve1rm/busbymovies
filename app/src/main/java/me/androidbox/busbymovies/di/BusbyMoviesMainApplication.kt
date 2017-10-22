package me.androidbox.busbymovies.di

import android.app.Application

/**
 * Created by steve on 10/22/17.
 */
class BusbyMoviesMainApplication : Application() {
    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent
                .builder()
                .apiModule(ApiModule())
                .build()
    }
}
