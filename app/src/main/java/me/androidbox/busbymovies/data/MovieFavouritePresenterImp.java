package me.androidbox.busbymovies.data;

import javax.inject.Inject;

import me.androidbox.busbymovies.di.DaggerInjector;
import me.androidbox.busbymovies.models.Favourite;
import me.androidbox.busbymovies.models.Results;

/**
 * Created by steve on 3/26/17.
 */

public class MovieFavouritePresenterImp implements
        MovieFavouriteModelContract.DeleteListener,
        MovieFavouriteModelContract.InsertListener,
        MovieFavouriteModelContract.RetrieveListener,
        MovieFavouritesPresenterContract {

    @Inject MovieFavouriteModelContract mMovieFavouriteModelContract;

    private DbOperationsListener mDbOperationsListener = null;

    public MovieFavouritePresenterImp() {
        DaggerInjector.getApplicationComponent().inject(MovieFavouritePresenterImp.this);
    }

    @Override
    public void getFavouriteMovies(MovieFavouritesPresenterContract.DbOperationsListener dbOperationsListener) {
        mDbOperationsListener = dbOperationsListener;
        if(mMovieFavouriteModelContract != null) {
            mMovieFavouriteModelContract.retrieve(MovieFavouritePresenterImp.this);
        }
    }

    @Override
    public void insertFavouriteMovie(Favourite favourite, MovieFavouritesPresenterContract.DbOperationsListener dbOperationsListener) {
        mDbOperationsListener = dbOperationsListener;
        if(mMovieFavouriteModelContract != null) {
            mMovieFavouriteModelContract.insert(favourite, MovieFavouritePresenterImp.this);
        }
    }

    @Override
    public void deleteFavouriteMovie(int movieId, MovieFavouritesPresenterContract.DbOperationsListener dbOperationsListener) {
        mDbOperationsListener = dbOperationsListener;
        if(mMovieFavouriteModelContract != null) {
            mMovieFavouriteModelContract.delete(movieId, MovieFavouritePresenterImp.this);
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
        if(mDbOperationsListener != null) {
            mDbOperationsListener.onGetFavouriteMoviesFailure(errorMessage);
        }
    }

    @Override
    public void onRetrievedSuccess(Results<Favourite> favouriteList) {
        if(mDbOperationsListener != null) {
            mDbOperationsListener.onGetFavouriteMoviesSuccess(favouriteList);
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
}
