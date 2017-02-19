package me.androidbox.busbymovies.movielist;

/**
 * Created by steve on 2/18/17.
 */

public interface MovieListViewContract {
    void displayPopularMovies(String movies);
    void displayTopRatedMovies(String movies);
    void failedToDisplayPopularMovies(String errorMessage);
    void failedToDisplayTopRatedMovies(String errorMessage);

}
