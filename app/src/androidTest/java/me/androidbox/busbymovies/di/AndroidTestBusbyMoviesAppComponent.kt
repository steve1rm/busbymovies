package me.androidbox.busbymovies.di

import dagger.Component
import me.androidbox.busbymovies.movielist.MovieListViewImpTest
import javax.inject.Singleton

/**
 * Created by steve on 11/29/17.
 */
@Singleton
@Component(modules = arrayOf(AndroidModule::class, ApiModule::class))
interface AndroidTestBusbyMoviesAppComponent : BusbyMoviesAppComponent {
    fun inject(movieListView: MovieListViewImpTest)
}
