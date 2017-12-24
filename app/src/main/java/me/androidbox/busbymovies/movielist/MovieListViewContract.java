package me.androidbox.busbymovies.movielist;

import me.androidbox.busbymovies.models.Movies;
import me.androidbox.busbymovies.models.Results;

/**
 * Created by steve on 2/18/17.
 */

public interface MovieListViewContract {
    void getPopularMovies();
    void displayPopularMovies(Results<Movies> popularMovies);
    void displayTopRatedMovies(Results<Movies> topRatedMovies);
    void failedToDisplayPopularMovies(String errorMessage);
    void failedToDisplayTopRatedMovies(String errorMessage);
    void failedToGetSearchMovies(final String errorMessage);
    void successToGetSearchMovies(final Results<Movies> movieSearch);

}
