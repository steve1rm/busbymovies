package me.androidbox.busbymovies.movielist;

import me.androidbox.busbymovies.models.Movie;
import me.androidbox.busbymovies.models.Results;
import rx.Observable;

/**
 * Created by steve on 2/19/17.
 */

public interface MovieListModelContract {
    void releaseResources();

    interface PopularMovieResultsListener {
        void onFailure(String errorMessage);
        void onSuccess(Results popularMovies);
    }

    void getPopularMovies(PopularMovieResultsListener popularMovieResultsListener);

    /** Testing only */
    Observable<Results> getPopularMovies();

    interface MovieResultsListener {
        void onFailure(String errorMessage);
        void onSuccess(Movie movie);
    }
    void getMovie(int movieId, MovieResultsListener movieResultsListener);

    //void searchPopulareMovies()
/*    void getTopRatedMovies(MovieResultsListener resultsListener);*/
}
