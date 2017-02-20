package me.androidbox.busbymovies.movielist;

import javax.inject.Inject;

import me.androidbox.busbymovies.di.DaggerInjector;

/**
 * Created by steve on 2/18/17.
 */

public class MovieListPresenterImp implements MovieListPresenterContract<MovieListViewContract>, MovieListModelContract.MovieResultsListener {
    private MovieListViewContract mMovieListViewContract;
    @Inject MovieListModelContract mMovieModelContract;

    public MovieListPresenterImp() {
        DaggerInjector.getApplicationComponent().inject(MovieListPresenterImp.this);
    }

    /**
     * Attach the view to the presenter
     * @param MovieListViewContract
     */
    @Override
    public void attachView(MovieListViewContract view) {
        mMovieListViewContract = view;
    }

    @Override
    public void detachView() {
        mMovieListViewContract = null;
    }

    @Override
    public void getPopularMovies() {
        if(mMovieListViewContract != null) {
            mMovieModelContract.getPopularMovies(MovieListPresenterImp.this);
        }
    }

    @Override
    public void getTopRatedMovies() {
        if(mMovieListViewContract != null) {
            mMovieListViewContract.displayTopRatedMovies("top rated");
        }
    }

    /**
     * Wait for the response to be called back in the Model on failure
     */
    @Override
    public void onFailure() {
        mMovieListViewContract.displayPopularMovies("popular");
    }

    /**
     * Wait for the response to be called back in the model on success
     */
    @Override
    public void onSuccess() {
        mMovieListViewContract.displayPopularMovies("popular");
    }
}
