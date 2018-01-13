package me.androidbox.busbymovies.di

import android.app.Application
import me.androidbox.busbymovies.moviedetails.MovieDetailViewImp
import me.androidbox.busbymovies.movielist.MovieListActivity
import me.androidbox.busbymovies.movielist.MovieListViewImp
import timber.log.Timber

/**
 * Created by steve on 10/22/17.
 */
open class BusbyMoviesMainApplication : Application() {
    private lateinit var appComponent: BusbyMoviesAppComponent

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

    }

    open fun createAppComponent(): BusbyMoviesAppComponent {
        return DaggerBusbyMoviesAppComponent.builder()
                .apiModule(ApiModule(this))
                .androidModule(AndroidModule(this))
                .build()
    }

    open fun getMovieListComponent(movieListViewImp: MovieListViewImp, movieListActivity: MovieListActivity): MovieListComponent {
        return appComponent.add(MovieListModule(movieListViewImp, movieListActivity))
    }


    fun getMovieDetailComponent(movieDetailViewImp: MovieDetailViewImp): MovieDetailComponent {
        return appComponent.add(MovieDetailModule(movieDetailViewImp))
    }
}
