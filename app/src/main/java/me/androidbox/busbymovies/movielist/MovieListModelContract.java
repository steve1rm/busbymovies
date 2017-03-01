package me.androidbox.busbymovies.movielist;

import me.androidbox.busbymovies.models.Movie;
import me.androidbox.busbymovies.models.Results;
import rx.Observable;

/**
 * Created by steve on 2/19/17.
 */

public interface MovieListModelContract {
    void releaseResources();

    /** Get all the popular movies */
    interface PopularMovieResultsListener {
        void onFailure(String errorMessage);
        void onSuccess(Results popularMovies);
    }
    void getPopularMovies(PopularMovieResultsListener popularMovieResultsListener);

    /** Get a single movie to display the details by its ID */
    interface MovieDetailResultsListener {
        void onFailure(String errorMessage);
        void onSuccess(Movie movie);
    }
    void getMovieDetail(int movieId, MovieDetailResultsListener movieDetailResultsListener);


    /** Testing only */
    Observable<Results> getPopularMovies();



    //void searchPopulareMovies()
/*    void getTopRatedMovies(MovieResultsListener resultsListener);*/
}
