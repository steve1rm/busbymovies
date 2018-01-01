package me.androidbox.busbymovies.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.Reusable
import me.androidbox.busbymovies.adapters.MovieAdapter
import me.androidbox.busbymovies.data.MovieFavouriteModelContract
import me.androidbox.busbymovies.data.MovieFavouriteModelImp
import me.androidbox.busbymovies.data.MovieFavouritePresenterImp
import me.androidbox.busbymovies.data.MovieFavouritePresenterContract
import me.androidbox.busbymovies.di.scopes.MovieListScope
import me.androidbox.busbymovies.models.Movie
import me.androidbox.busbymovies.models.Movies
import me.androidbox.busbymovies.movielist.*
import me.androidbox.busbymovies.network.MovieAPIService
import me.androidbox.busbymovies.utils.MovieSchedulers
import java.util.*

/**
 * Created by steve on 10/22/17.
 */
@Module
class MovieListModule(private val movieListViewImp: MovieListViewImp) {

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
    fun providesMovieFavouriteDbOptionsListener(): MovieFavouritePresenterContract.DbOperationsListener {
        return object : MovieFavouritePresenterContract.DbOperationsListener{
            override fun onInsertFavouriteSuccess() {
            }

            override fun onInsertFavouriteFailure(errorMessage: String?) {
            }

            override fun onDeleteFavouriteMovieSuccess(rowDeletedId: Int) {
            }

            override fun onDeleteFavouriteMovieFailure(errorMessage: String?) {
            }

            override fun onHasMovieFavouriteSuccess(movieId: Int, isFavourite: Boolean) {
            }

            override fun onHasMovieFavouriteFailure(errorMessage: String?) {
            }

            override fun onGetMovieFavouriteSuccess(favourite: Movie?) {
            }

            override fun onGetMovieFavouriteFailure(errorMessage: String?) {
            }
        }
    }

    @MovieListScope
    @Provides
    fun providesMovieFavouriteListListener(): MovieFavouritePresenterContract.MovieFavouriteListListener {
        return movieListViewImp
    }

    @MovieListScope
    @Provides
    fun providesMovieFavouritePresenter(movieModelFavouriteModelContract: MovieFavouriteModelContract,
                                        movieFavouriteListListener: MovieFavouritePresenterContract.MovieFavouriteListListener,
                                        dbOperationsListener: MovieFavouritePresenterContract.DbOperationsListener)
            : MovieFavouritePresenterContract {

        return MovieFavouritePresenterImp(
                movieModelFavouriteModelContract,
                dbOperationsListener,
                movieFavouriteListListener)
    }

    @MovieListScope
    @Provides
    fun providesMovieAdapter(): MovieAdapter {
        return MovieAdapter(Collections.emptyList<Movies>())
    }
}
        