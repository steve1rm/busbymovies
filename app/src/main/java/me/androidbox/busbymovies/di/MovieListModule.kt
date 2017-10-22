package me.androidbox.busbymovies.di

import dagger.Module
import dagger.Provides
import me.androidbox.busbymovies.di.scopes.MovieListScope
import me.androidbox.busbymovies.movielist.MovieListPresenterContract
import me.androidbox.busbymovies.movielist.MovieListPresenterImp
import me.androidbox.busbymovies.movielist.MovieListViewContract

/**
 * Created by steve on 10/22/17.
 */
@Module
class MovieListModule {

    @MovieListScope
    @Provides
    fun providesMovieListPresenter(): MovieListPresenterContract<MovieListViewContract> {
        return MovieListPresenterImp()
    }
}
        