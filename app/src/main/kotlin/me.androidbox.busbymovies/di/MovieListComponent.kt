package me.androidbox.busbymovies.di

import dagger.Subcomponent
import me.androidbox.busbymovies.di.scopes.MovieListScope
import me.androidbox.busbymovies.movielist.MovieListPresenterImp
import me.androidbox.busbymovies.movielist.MovieListViewImp

/**
 * Created by steve on 10/24/17.
 */
@MovieListScope
@Subcomponent(modules = arrayOf(MovieListModule::class))
interface MovieListComponent {
    fun inject(target: MovieListPresenterImp)
    fun inject(target: MovieListViewImp)
}
