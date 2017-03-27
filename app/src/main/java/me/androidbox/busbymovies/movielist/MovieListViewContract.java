package me.androidbox.busbymovies.movielist;

import java.util.List;

import me.androidbox.busbymovies.models.Favourite;
import me.androidbox.busbymovies.models.Results;

/**
 * Created by steve on 2/18/17.
 */

public interface MovieListViewContract {
    void getPopularMovies();
    void displayPopularMovies(Results popularMovies);
    void displayTopRatedMovies(Results topRatedMovies);
    void failedToDisplayPopularMovies(String errorMessage);
    void failedToDisplayTopRatedMovies(String errorMessage);
    void displayFavouriteMovies(List<Favourite> favouriteList);
    void failedDisplayFavouriteMovies(String errorMessage);
    void failedFavouriteMovieDelete(String errorMessage);
    void successFavouriteMovieDelete();
    void failedFavouriteMovieInsert(String errorMessage);
    void successFavouriteMovieInsert();
}
