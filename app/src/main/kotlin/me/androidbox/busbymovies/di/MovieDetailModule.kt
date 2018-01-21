package me.androidbox.busbymovies.di

import android.content.ContentResolver
import android.content.Context
import android.content.res.Resources
import dagger.Module
import dagger.Provides
import me.androidbox.busbymovies.adapters.MovieActorsAdapter
import me.androidbox.busbymovies.data.MovieFavouriteModelContract
import me.androidbox.busbymovies.data.MovieFavouriteModelImp
import me.androidbox.busbymovies.data.MovieFavouritePresenterImp
import me.androidbox.busbymovies.data.MovieFavouritePresenterContract
import me.androidbox.busbymovies.di.scopes.MovieDetailScope
import me.androidbox.busbymovies.models.Movie
import me.androidbox.busbymovies.models.Results
import me.androidbox.busbymovies.moviedetails.*
import me.androidbox.busbymovies.network.MovieAPIService
import me.androidbox.busbymovies.utils.ImageLoader
import me.androidbox.busbymovies.utils.MovieSchedulers

/**
 * Created by steve on 10/26/17.
 */
@Module
class MovieDetailModule(private val movieDetailViewImp: MovieDetailViewImp) {

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
    fun providesMovieFavouriteModel(contentResolver: ContentResolver, resources: Resources): MovieFavouriteModelContract {
        return MovieFavouriteModelImp(contentResolver, resources)
    }

    @MovieDetailScope
    @Provides
    fun providesDbOperationsListener(): MovieFavouritePresenterContract.DbOperationsListener {
        return movieDetailViewImp
    }

    @MovieDetailScope
    @Provides
    fun providesMovieFavouriteListListener(): MovieFavouritePresenterContract.MovieFavouriteListListener {
        return object: MovieFavouritePresenterContract.MovieFavouriteListListener{
            override fun onGetFavouriteMoviesSuccess(favouriteList: Results<Movie>?) {
            }

            override fun onGetFavouriteMoviesFailure(errorMessage: String?) {
            }
        }
    }

    @MovieDetailScope
    @Provides
    fun providesMovieFavouritePresenter(movieModelFavouriteModelContract: MovieFavouriteModelContract,
                                        dbOperationsListener: MovieFavouritePresenterContract.DbOperationsListener,
                                        movieFavouriteListListener: MovieFavouritePresenterContract.MovieFavouriteListListener)
            : MovieFavouritePresenterContract {

        return MovieFavouritePresenterImp(
                movieModelFavouriteModelContract,
                dbOperationsListener,
                movieFavouriteListListener)
    }

    @MovieDetailScope
    @Provides
    fun providesMovieActorsAdapter(imageLoader: ImageLoader): MovieActorsAdapter {
        return MovieActorsAdapter(imageLoader)
    }
}
