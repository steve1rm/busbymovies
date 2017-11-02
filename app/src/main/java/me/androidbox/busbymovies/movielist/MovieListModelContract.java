package me.androidbox.busbymovies.movielist;

import me.androidbox.busbymovies.models.Movie;
import me.androidbox.busbymovies.models.Movies;
import me.androidbox.busbymovies.models.Results;

/**
 * Created by steve on 2/19/17.
 */

public interface MovieListModelContract {
    void releaseResources();

    /** Get all the popular movies */
    interface PopularMovieResultsListener {
        void onPopularMovieFailure(final String errorMessage);
        void onPopularMovieSuccess(final Results<Movies> popularMovies);
    }
    void getPopularMovies(final PopularMovieResultsListener popularMovieResultsListener);

    /** Get all the top rated movies */
    interface TopRatedMovieResultsListener {
        void onTopRatedMovieFailure(final String errorMessage);
        void onTopRatedMovieSuccess(final Results<Movies> topRatedMovies);
    }
    void getTopRatedMovies(final TopRatedMovieResultsListener topRatedMovieResultsListener);

    /** Get a single movie to display the details by its ID */
    interface MovieDetailResultsListener {
        void onFailure(final String errorMessage);
        void onSuccess(final Movie movie);
    }
    void getMovieDetail(int movieId, MovieDetailResultsListener movieDetailResultsListener);

    /** Search for movies */
    interface MovieSearchResultsListener {
        void onSearchFailure(final String errorMessage);
        void onSearchSuccess(final Results<Movies> searchMovies);
    }
    void searchForMovies(final String movieName, final int movieYear, MovieSearchResultsListener movieSearchResultsListener);
}
