package me.androidbox.busbymovies.data;

import java.util.List;

import javax.inject.Inject;

import me.androidbox.busbymovies.di.DaggerInjector;
import me.androidbox.busbymovies.models.Favourite;
import me.androidbox.busbymovies.movielist.MovieListViewContract;

/**
 * Created by steve on 3/26/17.
 */

public class MovieFavouritePresenterImp implements
        MovieFavouritesPresenterContract<MovieListViewContract>,
        MovieFavouriteModelContract.DeleteListener,
        MovieFavouriteModelContract.InsertListener,
        MovieFavouriteModelContract.RetrieveListener {

    @Inject MovieFavouriteModelContract mMovieFavouriteModelContract;

    private MovieListViewContract mMovieListViewContract;

    public MovieFavouritePresenterImp() {
        DaggerInjector.getApplicationComponent().inject(MovieFavouritePresenterImp.this);
    }

    @Override
    public void attachView(MovieListViewContract view) {
        mMovieListViewContract = view;
        mMovieFavouriteModelContract.startup();
    }

    @Override
    public void detachView() {
        mMovieListViewContract = null;
        mMovieFavouriteModelContract.closeDown();
    }

    @Override
    public void getFavouriteMovies() {
        if(mMovieFavouriteModelContract != null) {
            mMovieFavouriteModelContract.retrieve(MovieFavouritePresenterImp.this);
        }
    }

    @Override
    public void insertFavouriteMovie(Favourite favourite) {
        if(mMovieFavouriteModelContract != null) {
            mMovieFavouriteModelContract.insert(favourite, MovieFavouritePresenterImp.this);
        }
    }

    @Override
    public void deleteFavouriteMovie(int movieId) {
        if(mMovieFavouriteModelContract != null) {
            mMovieFavouriteModelContract.delete(movieId, MovieFavouritePresenterImp.this);
        }
    }

    @Override
    public void onInsertFailed() {
        if(mMovieListViewContract != null) {
            mMovieListViewContract.failedFavouriteMovieInsert("Failed to insert favourite");
        }
    }

    @Override
    public void onInsertSuccess() {
        if(mMovieListViewContract != null) {
            mMovieListViewContract.successFavouriteMovieInsert();
        }
    }

    @Override
    public void onRetrieveFailed() {
        if(mMovieListViewContract != null) {
            mMovieListViewContract.failedDisplayFavouriteMovies("Failed to get favourite movies");
        }
    }

    @Override
    public void onRetrievedSuccess(List<Favourite> favouriteList) {
        if(mMovieListViewContract != null) {
            mMovieListViewContract.displayFavouriteMovies(favouriteList);
        }
    }

    @Override
    public void onDeleteFailed() {
        if(mMovieListViewContract != null) {
            mMovieListViewContract.failedFavouriteMovieDelete("Failed to delete favourite movie");
        }
    }

    @Override
    public void onDeleteSuccess() {
        if(mMovieListViewContract != null) {
            mMovieListViewContract.successFavouriteMovieDelete();
        }
    }
}
