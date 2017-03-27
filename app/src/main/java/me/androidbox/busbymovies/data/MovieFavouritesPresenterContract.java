package me.androidbox.busbymovies.data;

import me.androidbox.busbymovies.models.Favourite;
import me.androidbox.busbymovies.movielist.MovieListViewContract;

/**
 * Created by steve on 3/26/17.
 */

public interface MovieFavouritesPresenterContract<View extends MovieListViewContract> {
    void attachView(View view);
    void detachView();

    void getFavouriteMovies();
    void insertFavouriteMovie(Favourite favourite);
    void deleteFavouriteMovie(int movieId);
}
