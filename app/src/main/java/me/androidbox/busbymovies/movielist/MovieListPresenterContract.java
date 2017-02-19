package me.androidbox.busbymovies.movielist;

/**
 * Created by steve on 2/18/17.
 */

public interface MovieListPresenterContract<MovieListView> {
    void attachView(MovieListView view);
    void detachView();
    void getPopularMovies();
    void getTopRatedMovies();
}
