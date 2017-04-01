package me.androidbox.busbymovies.moviedetails;

import javax.inject.Inject;

import me.androidbox.busbymovies.di.DaggerInjector;
import me.androidbox.busbymovies.models.Movie;
import me.androidbox.busbymovies.models.Results;
import me.androidbox.busbymovies.models.Trailer;
import me.androidbox.busbymovies.utils.Misc;
import timber.log.Timber;

/**
 * Created by steve on 3/2/17.
 */

public class MovieDetailPresenterImp implements
        MovieDetailPresenterContract<MovieDetailViewContract>,
        MovieDetailModelContract.GetMovieDetailListener,
        MovieDetailModelContract.GetMovieTrailerListener {

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
            mMovieDetailModelContract.releaseResources();
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

    @Override
    public void requestMovieTrailer(int movieId) {
        Timber.d("requestMovieTrailers %d", movieId);
        if(mMovieDetailModelContract != null) {
            mMovieDetailModelContract.getMovieTrailer(movieId, MovieDetailPresenterImp.this);
        }
    }

    @Override
    public void onGetMovieTrailerSuccess(Results<Trailer> trailers) {
        if(mMovieDetailViewContract != null) {
            mMovieDetailViewContract.startPlayingMovieTrailer(trailers);
        }
    }

    @Override
    public void onGetMovieTrailerFailure(String errorMessage) {
        if(mMovieDetailViewContract != null) {
            mMovieDetailViewContract.failedToGetMovieTrailers(errorMessage);
        }
    }
}
