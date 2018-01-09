package di

import android.content.Context
import dagger.Module
import dagger.Provides
import me.androidbox.busbymovies.adapters.MovieAdapter
import me.androidbox.busbymovies.data.MovieFavouriteModelContract
import me.androidbox.busbymovies.data.MovieFavouriteModelImp
import me.androidbox.busbymovies.data.MovieFavouritePresenterContract
import me.androidbox.busbymovies.data.MovieFavouritePresenterImp
import me.androidbox.busbymovies.di.scopes.MovieListScope
import me.androidbox.busbymovies.movielist.*
import me.androidbox.busbymovies.network.MovieAPIService
import me.androidbox.busbymovies.utils.MovieSchedulers
import org.mockito.Mockito.mock
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
    fun providesDbOperations(): MovieFavouritePresenterContract.DbOperationsListener {
        return mock(MovieFavouritePresenterContract.DbOperationsListener::class.java)
    }

    @MovieListScope
    @Provides
    fun providesMovieFavouriteListListener(): MovieFavouritePresenterContract.MovieFavouriteListListener {
        return mock(MovieFavouritePresenterContract.MovieFavouriteListListener::class.java)
    }

    @MovieListScope
    @Provides
    fun providesMovieAdapter(): MovieAdapter {
        return mock(MovieAdapter::class.java)
    }

    @MovieListScope
    @Provides
    fun providesMovieFavouritePresenter(movieFavouriteModelContract: MovieFavouriteModelContract,
                                        dbOperationsListener: MovieFavouritePresenterContract.DbOperationsListener,
                                        movieFavouriteListListener: MovieFavouritePresenterContract.MovieFavouriteListListener)
            : MovieFavouritePresenterContract {

        return MovieFavouritePresenterImp(movieFavouriteModelContract, dbOperationsListener, movieFavouriteListListener)
    }
}
