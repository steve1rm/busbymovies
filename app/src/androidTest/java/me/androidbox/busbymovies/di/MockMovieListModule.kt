package me.androidbox.busbymovies.di

import android.content.Context
import dagger.Module
import dagger.Provides
import me.androidbox.busbymovies.data.MovieFavouriteModelContract
import me.androidbox.busbymovies.data.MovieFavouriteModelImp
import me.androidbox.busbymovies.data.MovieFavouritePresenterContract
import me.androidbox.busbymovies.data.MovieFavouritePresenterImp
import me.androidbox.busbymovies.di.scopes.MovieListScope
import me.androidbox.busbymovies.movielist.*
import me.androidbox.busbymovies.network.MovieAPIService
import me.androidbox.busbymovies.utils.MovieSchedulers
import javax.inject.Singleton

/**
 * Created by steve on 12/10/17.
 */
@Module
class MockMovieListModule {

    @MovieListScope
    @Provides
    fun providesMovieListModel(moviesAPIService: MovieAPIService, movieSchedulers: MovieSchedulers)
            : MovieListModelContract {

        return MovieListModelImp(moviesAPIService, movieSchedulers)
    }

    @MovieListScope
    @Provides
    fun providesMovieListPresenter(movieListModelContract: MovieListModelContract)
            : MovieListPresenterContract<MovieListViewContract> {

        return MovieListPresenterImp(movieListModelContract)
    }

    @MovieListScope
    @Provides
    fun providesMovieFavouriteModel(context: Context): MovieFavouriteModelContract {
        return MovieFavouriteModelImp(context)
    }

    @MovieListScope
    @Provides
    fun providesMovieFavouritePresenter(movieFavouriteModelContract: MovieFavouriteModelContract)
            : MovieFavouritePresenterContract {

        return MovieFavouritePresenterImp(movieFavouriteModelContract)
    }
}
