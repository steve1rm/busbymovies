package me.androidbox.busbymovies.movielist;

import me.androidbox.busbymovies.models.Results;

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
    void getMovie(int movieId, PopularMovieResultsListener popularMovieResultsListener);

    //void searchPopulareMovies()
/*    void getTopRatedMovies(MovieResultsListener resultsListener);*/
}
