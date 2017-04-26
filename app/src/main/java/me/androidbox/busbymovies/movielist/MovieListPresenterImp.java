package me.androidbox.busbymovies.movielist;

import android.support.annotation.VisibleForTesting;

import javax.inject.Inject;

import me.androidbox.busbymovies.di.DaggerInjector;
import me.androidbox.busbymovies.models.Movies;
import me.androidbox.busbymovies.models.Results;
import timber.log.Timber;

/**
 * Created by steve on 2/18/17.
 */

public class MovieListPresenterImp implements
        MovieListPresenterContract<MovieListViewContract>,
        MovieListModelContract.PopularMovieResultsListener,
        MovieListModelContract.TopRatedMovieResultsListener {

    private MovieListViewContract mMovieListViewContract;
    @Inject MovieListModelContract mMovieModelContract;

    public MovieListPresenterImp() {
        DaggerInjector.getApplicationComponent().inject(MovieListPresenterImp.this);
    }

    /** Running Junit Testing only */
    @VisibleForTesting MovieListPresenterImp(MovieListModelContract mMovieModelContract) {
        this.mMovieModelContract = mMovieModelContract;
    }

    /**
     * Attach the view to the presenter
     */
    @Override
    public void attachView(MovieListViewContract view) {
        mMovieListViewContract = view;
    }

    @Override
    public void detachView() {
        Timber.d("detachView");
        mMovieListViewContract = null;
        mMovieModelContract.releaseResources();
    }

    @Override
    public void getPopularMovies() {
        if(mMovieModelContract != null) {
            mMovieModelContract.getPopularMovies(MovieListPresenterImp.this);
        }
    }

    @Override
    public void getTopRatedMovies() {
        if(mMovieModelContract != null) {
            mMovieModelContract.getTopRatedMovies(MovieListPresenterImp.this);
        }
    }

    /**
     * Wait for the response to be called back in the Model on failure
     */

    @Override
    public void onPopularMovieFailure(String errorMessage) {
        mMovieListViewContract.failedToDisplayPopularMovies(errorMessage);
    }

    /**
     * Wait for the response to be called back in the model on success
     */
    @Override
    public void onPopularMovieSuccess(Results<Movies> popularMovies) {
        mMovieListViewContract.displayPopularMovies(popularMovies);
    }

    /**
     * Wait for the response to be called back in the model for getting top rated movies
     * @param errorMessage
     */
    @Override
    public void onTopRatedMovieFailure(String errorMessage) {
        mMovieListViewContract.failedToDisplayTopRatedMovies(errorMessage);
    }

    /**
     * Wait for the response to be called back in the model for getting top rated movies
     * @param topRatedMovies
     */
    @Override
    public void onTopRatedMovieSuccess(Results<Movies> topRatedMovies) {
        mMovieListViewContract.displayTopRatedMovies(topRatedMovies);
    }
}
