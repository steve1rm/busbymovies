package me.androidbox.busbymovies.moviedetails;

import me.androidbox.busbymovies.models.Movie;

/**
 * Created by steve on 3/2/17.
 */

public class MovieDetailPresenterImp implements MovieDetailPresenterContract<MovieDetailViewContract>, MovieDetailModelContract.GetMovieDetailListener {

    private MovieDetailViewContract mMovieDetailViewContract;

    @Override
    public void attachView(MovieDetailViewContract movieDetailViewContract) {
        mMovieDetailViewContract = movieDetailViewContract;
    }

    @Override
    public void detachView() {
        if(mMovieDetailViewContract != null) {
            mMovieDetailViewContract = null;
        }
    }

    @Override
    public void getMovieDetail() {

    }

    @Override
    public void onGetMovieDetailFailure(String errMessage) {
        if(mMovieDetailViewContract != null) {
            mMovieDetailViewContract.displayErrorFailedToGetMovie(errMessage);
        }
    }

    @Override
    public void onGetMovieDetailSuccess(Movie movie) {

    }
}
