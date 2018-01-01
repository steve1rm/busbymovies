package di

import dagger.Module
import dagger.Provides
import me.androidbox.busbymovies.data.MovieFavouriteModelContract
import me.androidbox.busbymovies.data.MovieFavouriteModelImp
import me.androidbox.busbymovies.data.MovieFavouritePresenterContract
import me.androidbox.busbymovies.di.scopes.MovieListScope
import me.androidbox.busbymovies.models.Movie
import me.androidbox.busbymovies.movielist.MovieListModelContract
import me.androidbox.busbymovies.movielist.MovieListPresenterContract
import me.androidbox.busbymovies.movielist.MovieListPresenterImp
import org.mockito.Mockito.mock

/**
 * Created by steve on 12/31/17.
 */
@Module
class TestMovieListModule {

    @MovieListScope
    @Provides
    fun providesMovieListModel(): MovieListModelContract {

        return mock(MovieListModelContract::class.java)
    }

    @MovieListScope
    @Provides
    fun providesMovieListPresenter(): MovieListPresenterContract {

        return mock(MovieListPresenterImp::class.java)
    }

    @MovieListScope
    @Provides
    fun providesMovieFavouriteModel(): MovieFavouriteModelContract {
        return mock(MovieFavouriteModelImp::class.java)
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
        return mock(MovieFavouritePresenterContract.MovieFavouriteListListener::class.java)
    }

    @MovieListScope
    @Provides
    fun providesMovieFavouritePresenter()
            : MovieFavouritePresenterContract {

        return mock(MovieFavouritePresenterContract::class.java)
    }
}
