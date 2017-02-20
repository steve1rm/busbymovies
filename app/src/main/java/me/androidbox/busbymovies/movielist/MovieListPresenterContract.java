package me.androidbox.busbymovies.movielist;

/**
 * Created by steve on 2/18/17.
 */

public interface MovieListPresenterContract<V extends MovieListViewContract> {
    void attachView(V view);
    void detachView();
    void getPopularMovies();
    void getTopRatedMovies();
}
