package me.androidbox.busbymovies.movielist;

import me.androidbox.busbymovies.basepresenter.BasePresenter;

/**
 * Created by steve on 2/18/17.
 */

public interface MovieListPresenterContract extends BasePresenter<MovieListViewContract> {
    void getPopularMovies();
    void getTopRatedMovies();
    void searchMovies(final String movieName, final int movieYear);
    void openSortFab();
    void closeSortFab();
}
