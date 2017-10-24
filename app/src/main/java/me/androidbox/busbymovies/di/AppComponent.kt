package me.androidbox.busbymovies.di

import dagger.Component
import me.androidbox.busbymovies.movielist.MovieListModelImp
import javax.inject.Singleton

/**
 * Created by steve on 10/22/17.
 */
@Singleton
@Component(modules = arrayOf(ApiModule::class, AndroidModule::class))
interface AppComponent {
    fun inject(target: MovieListModelImp)

    fun add(movieListModule: MovieListModule): MovieListComponent
}