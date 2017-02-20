package me.androidbox.busbymovies.movielist;

import me.androidbox.busbymovies.models.Results;

/**
 * Created by steve on 2/18/17.
 */

public interface MovieListViewContract {
    void displayPopularMovies(Results popularMovies);
    void displayTopRatedMovies(String movies);
    void failedToDisplayPopularMovies(String errorMessage);
    void failedToDisplayTopRatedMovies(String errorMessage);
}
