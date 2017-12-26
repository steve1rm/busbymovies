package me.androidbox.busbymovies.data;

import javax.inject.Inject;

import me.androidbox.busbymovies.models.Movie;
import me.androidbox.busbymovies.models.Results;

/**
 * Created by steve on 3/26/17.
 */

public final class MovieFavouritePresenterImp
        implements
        MovieFavouriteModelContract.DeleteListener,
        MovieFavouriteModelContract.InsertListener,
        MovieFavouriteModelContract.RetrieveListener,
        MovieFavouriteModelContract.QueryMovieListener,
        MovieFavouriteModelContract.GetMovieFavourite,
        MovieFavouritePresenterContract {

    private final MovieFavouriteModelContract mMovieFavouriteModelContract;

    private DbOperationsListener mDbOperationsListener = null;
    private MovieFavouriteListListener movieFavouriteListListener = null;

    @Inject
    public MovieFavouritePresenterImp(final MovieFavouriteModelContract movieFavouriteModelContract) {
        this.mMovieFavouriteModelContract = movieFavouriteModelContract;
    }

    @Override
    public void getMovieFavourite(int movieId, DbOperationsListener dbOperationsListener) {
        mDbOperationsListener = dbOperationsListener;
        if(mMovieFavouriteModelContract != null) {
            mMovieFavouriteModelContract.getMovieFavourite(movieId, MovieFavouritePresenterImp.this);
        }
    }

    @Override
    public void getFavouriteMovies(MovieFavouriteListListener movieFavouriteListListener) {
        this.movieFavouriteListListener = movieFavouriteListListener;
        if(mMovieFavouriteModelContract != null) {
            mMovieFavouriteModelContract.retrieve(MovieFavouritePresenterImp.this);
        }
    }

    @Override
    public void insertFavouriteMovie(Movie favourite, MovieFavouritePresenterContract.DbOperationsListener dbOperationsListener) {
        mDbOperationsListener = dbOperationsListener;
        if(mMovieFavouriteModelContract != null) {
            mMovieFavouriteModelContract.insert(favourite, MovieFavouritePresenterImp.this);
        }
    }

    @Override
    public void deleteFavouriteMovie(int movieId, MovieFavouritePresenterContract.DbOperationsListener dbOperationsListener) {
        mDbOperationsListener = dbOperationsListener;
        if(mMovieFavouriteModelContract != null) {
            mMovieFavouriteModelContract.delete(movieId, MovieFavouritePresenterImp.this);
        }
    }

    @Override
    public void hasMovieAsFavourite(int movieId, DbOperationsListener dbOperationsListener) {
        mDbOperationsListener = dbOperationsListener;
        if(mMovieFavouriteModelContract != null) {
            mMovieFavouriteModelContract.queryMovie(movieId, MovieFavouritePresenterImp.this);
        }
    }

    @Override
    public void onInsertFailed(String errorMessage) {
        if(mDbOperationsListener != null) {
            mDbOperationsListener.onInsertFavouriteFailure(errorMessage);
        }
    }

    @Override
    public void onInsertSuccess() {
        if(mDbOperationsListener != null) {
            mDbOperationsListener.onInsertFavouriteSuccess();
        }
    }

    @Override
    public void onRetrieveFailed(String errorMessage) {
        if(movieFavouriteListListener != null) {
            movieFavouriteListListener.onGetFavouriteMoviesFailure(errorMessage);
        }
    }

    @Override
    public void onRetrievedSuccess(Results<Movie> favouriteList) {
        if(movieFavouriteListListener != null) {
            movieFavouriteListListener.onGetFavouriteMoviesSuccess(favouriteList);
        }
    }

    @Override
    public void onDeleteFailed(String errorMessage) {
        if(mDbOperationsListener != null) {
            mDbOperationsListener.onDeleteFavouriteMovieFailure(errorMessage);
        }
    }

    @Override
    public void onDeleteSuccess(int rowId) {
        if(mDbOperationsListener != null) {
            mDbOperationsListener.onDeleteFavouriteMovieSuccess(rowId);
        }
    }

    @Override
    public void onQueryMovieFailed(String errorMessage) {
        if(mDbOperationsListener != null) {
            mDbOperationsListener.onHasMovieFavouriteFailure(errorMessage);
        }
    }

    @Override
    public void onQueryMovieSuccess(int movieId, boolean isFavourite) {
        if(mDbOperationsListener != null) {
            mDbOperationsListener.onHasMovieFavouriteSuccess(movieId, isFavourite);
        }
    }

    @Override
    public void onGetMovieFavouriteFailure(String errorMessage) {
        if(mDbOperationsListener != null) {
            mDbOperationsListener.onGetMovieFavouriteFailure(errorMessage);
        }
    }

    @Override
    public void onGetMovieFavouriteSuccess(Movie favourite) {
        if(mDbOperationsListener != null) {
            mDbOperationsListener.onGetMovieFavouriteSuccess(favourite);
        }
    }
}
