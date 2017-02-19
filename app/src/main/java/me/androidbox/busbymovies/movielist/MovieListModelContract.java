package me.androidbox.busbymovies.movielist;

/**
 * Created by steve on 2/19/17.
 */

public interface MovieListModelContract {

    interface MovieResultsListener {
        void onFailure();
        void onSuccess();
    }

    void getPopularMovies(MovieResultsListener resultsListener);
/*    void getTopRatedMovies(MovieResultsListener resultsListener);*/
}
