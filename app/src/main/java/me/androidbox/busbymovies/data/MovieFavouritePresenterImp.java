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
    }

    @Override
    public void getFavouriteMovies() {
        if(mMovieListViewContract != null) {
            mMovieFavouriteModelContract.retrieve(MovieFavouritePresenterImp.this);
        }
    }

    @Override
    public void insertFavouriteMovie() {

    }

    @Override
    public void deleteFavouriteMovie() {

    }

    @Override
    public void onInsertFailed() {

    }

    @Override
    public void onInsertSuccess() {

    }

    @Override
    public void onRetrieveFailed() {

    }

    @Override
    public void onRetrievedSuccess(List<Favourite> favouriteList) {

    }

    @Override
    public void onDeleteFailed() {

    }

    @Override
    public void onDeleteSuccess() {

    }
}
