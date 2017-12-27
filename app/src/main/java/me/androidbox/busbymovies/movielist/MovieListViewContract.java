package me.androidbox.busbymovies.movielist;

import me.androidbox.busbymovies.models.Movies;
import me.androidbox.busbymovies.models.Results;

/**
 * Created by steve on 2/18/17.
 */

public interface MovieListViewContract {
    void displayPopularMovies(final Results<Movies> popularMovies);
    void displayTopRatedMovies(final Results<Movies> topRatedMovies);
    void failedToDisplayPopularMovies(final String errorMessage);
    void failedToDisplayTopRatedMovies(final String errorMessage);
    void failedToGetSearchMovies(final String errorMessage);
    void successToGetSearchMovies(final Results<Movies> movieSearch);
    void onHideProgressBar();
    void onShowProgressBar();
    void onCloseSortFab();
    void onOpenSortFab();
}
