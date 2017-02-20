package me.androidbox.busbymovies.movielist;

import java.util.List;

import me.androidbox.busbymovies.models.PopularMovies;
import me.androidbox.busbymovies.models.Results;

/**
 * Created by steve on 2/19/17.
 */

public interface MovieListModelContract {

    interface PopularMovieResultsListener {
        void onFailure(String errorMessage);
        void onSuccess(Results popularMovies);
    }

    interface PopulareMovieResultListener {
        void onFailure(String errorMessage);
        void onSuccess(PopularMovies popularMovies);
    }

    void getPopularMovies(PopularMovieResultsListener popularMovieResultsListener);
    void getMovie(int movieId, PopularMovieResultsListener popularMovieResultsListener);

    //void searchPopulareMovies()
/*    void getTopRatedMovies(MovieResultsListener resultsListener);*/
}
