package me.androidbox.busbymovies.movielist;

import me.androidbox.busbymovies.models.Movie;
import me.androidbox.busbymovies.models.Movies;
import me.androidbox.busbymovies.models.Results;
import rx.Observable;

/**
 * Created by steve on 2/19/17.
 */

public interface MovieListModelContract {
    void releaseResources();

    /** Get all the popular movies */
    interface PopularMovieResultsListener {
        void onPopularMovieFailure(String errorMessage);
        void onPopularMovieSuccess(Results<Movies> popularMovies);
    }
    void getPopularMovies(PopularMovieResultsListener popularMovieResultsListener);

    /** Get all the top rated movies */
    interface TopRatedMovieResultsListener {
        void onTopRatedMovieFailure(String errorMessage);
        void onTopRatedMovieSuccess(Results<Movies> topRatedMovies);
    }
    void getTopRatedMovies(TopRatedMovieResultsListener topRatedMovieResultsListener);

    /** Get a single movie to display the details by its ID */
    interface MovieDetailResultsListener {
        void onFailure(String errorMessage);
        void onSuccess(Movie movie);
    }
    void getMovieDetail(int movieId, MovieDetailResultsListener movieDetailResultsListener);

    /** Search for movies */
    interface MovieSearchResultsListener {
        void onSearchFailure(final String errorMessage);
        void onSearchSuccess(Results<Movies> searchMovies);
    }
    void searchForMovies(final String movieName, final int movieYear, MovieSearchResultsListener movieSearchResultsListener);

    /** Testing only */
    Observable<Results<Movies>> getPopularMovies();
}
