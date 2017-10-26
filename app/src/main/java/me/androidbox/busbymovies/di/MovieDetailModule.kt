package me.androidbox.busbymovies.di

import android.content.Context
import dagger.Module
import dagger.Provides
import me.androidbox.busbymovies.data.MovieFavouriteModelContract
import me.androidbox.busbymovies.data.MovieFavouriteModelImp
import me.androidbox.busbymovies.data.MovieFavouritePresenterImp
import me.androidbox.busbymovies.data.MovieFavouritesPresenterContract
import me.androidbox.busbymovies.di.scopes.MovieDetailScope
import me.androidbox.busbymovies.moviedetails.*
import me.androidbox.busbymovies.network.MovieAPIService
import me.androidbox.busbymovies.utils.MovieSchedulers

/**
 * Created by steve on 10/26/17.
 */
@Module
class MovieDetailModule {

    @MovieDetailScope
    @Provides
    fun providesMovieDetailModel(movideAPIService: MovieAPIService, movieSchedulers: MovieSchedulers): MovieDetailModelContract {
        return MovieDetailModelImp(movideAPIService, movieSchedulers)
    }

    @MovieDetailScope
    @Provides
    fun providesMovieDetailPresenter(movieDetailModelContract: MovieDetailModelContract)
            : MovieDetailPresenterContract<MovieDetailViewContract> {

        return MovieDetailPresenterImp(movieDetailModelContract)
    }

    @MovieDetailScope
    @Provides
    fun providesMovieFavouriteModel(context: Context): MovieFavouriteModelContract {
        return MovieFavouriteModelImp(context)
    }

    @MovieDetailScope
    @Provides
    fun providesMovieFavouritePresenter(movieModelFavouriteModelContract: MovieFavouriteModelContract)
            : MovieFavouritesPresenterContract {
        return MovieFavouritePresenterImp(movieModelFavouriteModelContract)
    }
}
