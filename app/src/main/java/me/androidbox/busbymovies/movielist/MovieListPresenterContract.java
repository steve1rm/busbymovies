package me.androidbox.busbymovies.movielist;

/**
 * Created by steve on 2/18/17.
 */

public interface MovieListPresenterContract<View extends MovieListViewContract> {
    void attachView(View view);
    void detachView();
    void getPopularMovies();
    void getTopRatedMovies();
}
