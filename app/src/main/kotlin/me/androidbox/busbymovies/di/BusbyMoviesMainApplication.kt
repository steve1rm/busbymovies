package me.androidbox.busbymovies.di

import android.app.Application
import timber.log.Timber

/**
 * Created by steve on 10/22/17.
 */
open class BusbyMoviesMainApplication : Application() {
    private lateinit var appComponent: BusbyMoviesAppComponent
    private lateinit var movieListComponent: MovieListComponent
    private lateinit var movieDetailComponent: MovieDetailComponent

    companion object {
        private lateinit var instance: BusbyMoviesMainApplication

        @JvmStatic
        fun getBusbyInstance() = instance
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        Timber.plant(Timber.DebugTree())

        appComponent = createAppComponent()
        movieListComponent = createMovieListComponent()
        movieDetailComponent = createMovieDetailComponent()
    }

    open fun createAppComponent(): BusbyMoviesAppComponent {
        return DaggerBusbyMoviesAppComponent.builder()
                .apiModule(ApiModule(this))
                .androidModule(AndroidModule(this))
                .build()
    }

    open fun getMovieListComponent(): MovieListComponent {
        return movieListComponent
    }


    fun getMovieDetailComponent(): MovieDetailComponent {
        return movieDetailComponent
    }

    private fun createMovieListComponent(): MovieListComponent {
        return appComponent.add(MovieListModule())
    }

    private fun createMovieDetailComponent(): MovieDetailComponent {
        return appComponent.add(MovieDetailModule())
    }
}
