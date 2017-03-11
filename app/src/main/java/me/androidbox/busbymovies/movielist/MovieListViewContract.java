package me.androidbox.busbymovies.movielist;

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
}
