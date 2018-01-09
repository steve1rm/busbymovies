package me.androidbox.busbymovies.data;

import me.androidbox.busbymovies.models.Movie;
import me.androidbox.busbymovies.models.Results;

/**
 * Created by steve on 3/26/17.
 */

public interface MovieFavouritePresenterContract {
    interface DbOperationsListener {
        void onInsertFavouriteSuccess();
        void onInsertFavouriteFailure(String errorMessage);
        void onDeleteFavouriteMovieSuccess(int rowDeletedId);
        void onDeleteFavouriteMovieFailure(String errorMessage);
        void onHasMovieFavouriteSuccess(int movieId, boolean isFavourite);
        void onHasMovieFavouriteFailure(String errorMessage);
        void onGetMovieFavouriteSuccess(Movie favourite);
        void onGetMovieFavouriteFailure(final String errorMessage);
    }

    interface MovieFavouriteListListener {
        void onGetFavouriteMoviesSuccess(Results<Movie> favouriteList);
        void onGetFavouriteMoviesFailure(String errorMessage);
    }

    void getMovieFavourite(int movieId);
    void hasMovieAsFavourite(int movieId);
    void getFavouriteMovies();
    void insertFavouriteMovie(Movie favourite);
    void deleteFavouriteMovie(int movieId);
}
