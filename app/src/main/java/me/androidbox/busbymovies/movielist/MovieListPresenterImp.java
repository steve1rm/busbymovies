package me.androidbox.busbymovies.movielist;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.v4.util.Preconditions;

import javax.inject.Inject;

import me.androidbox.busbymovies.models.Movies;
import me.androidbox.busbymovies.models.Results;
import timber.log.Timber;

/**
 * Created by steve on 2/18/17.
 */

public class MovieListPresenterImp implements
        MovieListPresenterContract<MovieListViewContract>,
        MovieListModelContract.PopularMovieResultsListener,
        MovieListModelContract.TopRatedMovieResultsListener,
        MovieListModelContract.MovieSearchResultsListener {

    @VisibleForTesting MovieListViewContract mMovieListViewContract;
    private final MovieListModelContract mMovieModelContract;

    @Inject
    public MovieListPresenterImp(final MovieListModelContract movieListModelContract) {
        this.mMovieModelContract = movieListModelContract;
    }

    /** Running Junit Testing only */
/*
    @VisibleForTesting MovieListPresenterImp(MovieListModelContract mMovieModelContract) {
        this.mMovieModelContract = mMovieModelContract;
    }
*/

    /**
     * Attach the view to the presenter
     */
    @SuppressLint("RestrictedApi")
    @Override
    public void attachView(@NonNull MovieListViewContract view) {
        mMovieListViewContract = Preconditions.checkNotNull(view);
    }

    @Override
    public void detachView() {
        Timber.d("detachView");
        mMovieModelContract.releaseResources();
        mMovieListViewContract = null;
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

    @Override
    public void onSearchFailure(String errorMessage) {
        mMovieListViewContract.failedToGetSearchMovies(errorMessage);
    }

    @Override
    public void onSearchSuccess(Results<Movies> searchMovies) {
        mMovieListViewContract.successToGetSearchMovies(searchMovies);
    }

    @Override
    public void searchMovies(final String movieName, final int movieYear) {
        mMovieModelContract.searchForMovies(movieName, movieYear, MovieListPresenterImp.this);
    }
}
