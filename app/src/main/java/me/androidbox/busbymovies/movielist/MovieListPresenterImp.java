package me.androidbox.busbymovies.movielist;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import me.androidbox.busbymovies.basepresenter.BasePresenterImp;
import me.androidbox.busbymovies.models.Movies;
import me.androidbox.busbymovies.models.Results;

/**
 * Created by steve on 2/18/17.
 */

public final class MovieListPresenterImp
        extends
        BasePresenterImp<MovieListViewContract>
        implements
        MovieListPresenterContract,
        MovieListModelContract.PopularMovieResultsListener,
        MovieListModelContract.TopRatedMovieResultsListener,
        MovieListModelContract.MovieSearchResultsListener {

    private final MovieListModelContract mMovieModelContract;

    public MovieListPresenterImp(@NonNull final MovieListModelContract movieListModelContract) {
        this.mMovieModelContract = movieListModelContract;
    }

    @Override
    public void openSortFab() {
        if(isViewAttached()) {
            getView().onOpenSortFab();
        }
    }

    @Override
    public void closeSortFab() {
        if(isViewAttached()) {
            getView().onCloseSortFab();
        }
    }

    @Override
    public void getPopularMovies() {
        if(mMovieModelContract != null && isViewAttached()) {
            getView().onShowProgressBar();
            mMovieModelContract.getPopularMovies(MovieListPresenterImp.this);
        }
    }

    @Override
    public void getTopRatedMovies() {
        if(mMovieModelContract != null && isViewAttached()) {
            getView().onShowProgressBar();
            mMovieModelContract.getTopRatedMovies(MovieListPresenterImp.this);
        }
    }

    /**
     * Wait for the response to be called back in the Model on failure
     */
    @Override
    public void onPopularMovieFailure(String errorMessage) {
        if(isViewAttached()) {
            getView().onHideProgressBar();
            getView().failedToDisplayPopularMovies(errorMessage);
        }
    }

    /**
     * Wait for the response to be called back in the model on success
     */
    @Override
    public void onPopularMovieSuccess(Results<Movies> popularMovies) {
        if(isViewAttached()) {
            getView().onHideProgressBar();
            getView().displayPopularMovies(popularMovies);
        }
    }

    /**
     * Wait for the response to be called back in the model for getting top rated movies
     * @param errorMessage
     */
    @Override
    public void onTopRatedMovieFailure(String errorMessage) {
        if(isViewAttached()) {
            getView().onHideProgressBar();
            getView().failedToDisplayTopRatedMovies(errorMessage);
        }
    }

    /**
     * Wait for the response to be called back in the model for getting top rated movies
     * @param topRatedMovies
     */
    @Override
    public void onTopRatedMovieSuccess(Results<Movies> topRatedMovies) {
        if(isViewAttached()) {
            getView().onHideProgressBar();
            getView().displayTopRatedMovies(topRatedMovies);
        }
    }

    @Override
    public void onSearchFailure(String errorMessage) {
        if(isViewAttached()) {
            getView().failedToGetSearchMovies(errorMessage);
        }
    }

    @Override
    public void onSearchSuccess(Results<Movies> searchMovies) {
        if(isViewAttached()) {
            getView().successToGetSearchMovies(searchMovies);
        }
    }

    @Override
    public void searchMovies(final String movieName, final int movieYear) {
        mMovieModelContract.searchForMovies(movieName, movieYear, MovieListPresenterImp.this);
    }

    @Override
    public void detachView() {
        if(mMovieModelContract != null) {
            mMovieModelContract.releaseResources();
        }

        super.detachView();
    }
}
