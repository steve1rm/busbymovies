package me.androidbox.busbymovies.di

import android.content.Context
import dagger.Module
import dagger.Provides
import me.androidbox.busbymovies.data.MovieFavouriteModelContract
import me.androidbox.busbymovies.data.MovieFavouriteModelImp
import me.androidbox.busbymovies.data.MovieFavouritePresenterImp
import me.androidbox.busbymovies.data.MovieFavouritePresenterContract
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
    fun providesMovieListModel(movieApiService: MovieAPIService, movieSchedulers: MovieSchedulers)
            : MovieListModelContract {

        return MovieListModelImp(movieApiService, movieSchedulers)
    }

    @MovieListScope
    @Provides
    fun providesMovieListPresenter(movieListModelContract: MovieListModelContract)
            : MovieListPresenterContract {

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
        