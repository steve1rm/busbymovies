package me.androidbox.busbymovies.di

import dagger.Component
import me.androidbox.busbymovies.movielist.MovieListViewImpTest
import javax.inject.Singleton

/**
 * Created by steve on 11/29/17.
 */
@Singleton
@Component(modules = arrayOf(MockAndroidModule::class, MockApiModule::class))
interface AndroidTestBusbyMoviesAppComponent : BusbyMoviesAppComponent {
    fun add(mockMovieListModule: MockMovieListModule): AndroidTestMovieListComponent
}
