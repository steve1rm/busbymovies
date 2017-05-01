package me.androidbox.busbymovies.data;

import me.androidbox.busbymovies.models.Favourite;
import me.androidbox.busbymovies.models.Results;

/**
 * Created by steve on 3/26/17.
 */

public interface MovieFavouritesPresenterContract {
    interface DbOperationsListener {
        void onGetFavouriteMoviesSuccess(Results<Favourite> favouriteList);
        void onGetFavouriteMoviesFailure(String errorMessage);
        void onInsertFavouriteSuccess();
        void onInsertFavouriteFailure(String errorMessage);
        void onDeleteFavouriteMovieSuccess(int rowDeletedId);
        void onDeleteFavouriteMovieFailure(String errorMessage);
        void onHasMovieFavouriteSuccess(boolean hasMovieFavourite);
        void onHasMovieFavouriteFailure(String errorMessage);
    }

    void hasMovieAsFavourite(int movieId, DbOperationsListener dbOperationsListener);
    void getFavouriteMovies(DbOperationsListener dbOperationsListener);
    void insertFavouriteMovie(Favourite favourite, DbOperationsListener dbOperationsListener);
    void deleteFavouriteMovie(int movieId, DbOperationsListener dbOperationsListener);
}
