package me.androidbox.busbymovies.di

import android.app.Application
import timber.log.Timber

/**
 * Created by steve on 10/22/17.
 */
class BusbyMoviesMainApplication : Application() {
    private lateinit var appComponent: AppComponent
    private lateinit var movieListComponent: MovieListComponent

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())

        appComponent = createAppComponent()
        movieListComponent = createMovieListComponent()
    }

    private fun createAppComponent(): AppComponent {
        return DaggerAppComponent.builder()
                .apiModule(ApiModule())
                .androidModule(AndroidModule(this))
                .build()
    }

    private fun createMovieListComponent(): MovieListComponent {
        return appComponent.add(MovieListModule())
    }

    fun getMovieListComponent(): MovieListComponent {
        return movieListComponent
    }
}
