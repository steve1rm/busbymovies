package me.androidbox.busbymovies.moviedetails;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;

import me.androidbox.busbymovies.di.DaggerInjector;
import me.androidbox.busbymovies.models.Movie;
import me.androidbox.busbymovies.utils.Constants;
import me.androidbox.busbymovies.utils.Misc;
import timber.log.Timber;

/**
 * Created by steve on 3/2/17.
 */

public class MovieDetailPresenterImp implements MovieDetailPresenterContract<MovieDetailViewContract>, MovieDetailModelContract.GetMovieDetailListener {

    private MovieDetailViewContract mMovieDetailViewContract;

    @Inject MovieDetailModelContract mMovieDetailModelContract;

    public MovieDetailPresenterImp() {
        DaggerInjector.getApplicationComponent().inject(MovieDetailPresenterImp.this);
        if(mMovieDetailModelContract == null) {
            Timber.e("mMovieDetailModelContract == null");
        }
    }

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
    public void getMovieDetail(int movieId) {
        mMovieDetailModelContract.getMovieDetail(movieId, MovieDetailPresenterImp.this);
    }

    @Override
    public void onGetMovieDetailFailure(String errMessage) {
        if(mMovieDetailViewContract != null) {
            mMovieDetailViewContract.displayErrorFailedToGetMovie(errMessage);
        }
    }

    @Override
    public float getVoteAverage(float voteAverage) {
        return (voteAverage / 10) * 5;
    }

    @Override
    public String getMovieFormattedDate(String date, String format) {
        return Misc.formatDate(date, format);
    }

    @Override
    public void onGetMovieDetailSuccess(Movie movie) {
        Timber.d("onGetMovieDetailSuccess: %s", movie.getTitle());
        if(mMovieDetailViewContract != null) {
            mMovieDetailViewContract.displayMovieDetails(movie);
        }
    }


}
