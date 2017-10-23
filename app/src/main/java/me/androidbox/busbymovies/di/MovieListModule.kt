package me.androidbox.busbymovies.di

import dagger.Module
import dagger.Provides
import me.androidbox.busbymovies.di.scopes.MovieListScope
import me.androidbox.busbymovies.movielist.*
import me.androidbox.busbymovies.network.MovieAPIService
import me.androidbox.busbymovies.utils.MovieSchedulers

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

    @MovieListScope
    @Provides
    fun providesMovieListModel(movieApiService: MovieAPIService, movieSchedulers: MovieSchedulers): MovieListModelContract {
        return MovieListModelImp(movieApiService, movieSchedulers)
    }
}
        