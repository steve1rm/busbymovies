package me.androidbox.busbymovies.moviedetails;

import android.support.annotation.VisibleForTesting;

import me.androidbox.busbymovies.models.Actor;
import me.androidbox.busbymovies.models.Cast;
import me.androidbox.busbymovies.models.Movie;
import me.androidbox.busbymovies.models.Movies;
import me.androidbox.busbymovies.models.Results;
import me.androidbox.busbymovies.models.Review;
import me.androidbox.busbymovies.models.Trailer;
import me.androidbox.busbymovies.utils.Misc;
import timber.log.Timber;

/**
 * Created by steve on 3/2/17.
 */

public class MovieDetailPresenterImp implements
        MovieDetailPresenterContract<MovieDetailViewContract>,
        MovieDetailModelContract.GetMovieDetailListener,
        MovieDetailModelContract.GetMovieTrailerListener,
        MovieDetailModelContract.MovieReviewsListener,
        MovieDetailModelContract.MovieActorsListener,
        MovieDetailModelContract.SimilarMovieResultsListener {

    private MovieDetailViewContract mMovieDetailViewContract;
    private MovieDetailModelContract mMovieDetailModelContract;

    public MovieDetailPresenterImp(final MovieDetailModelContract movieDetailModelContract) {
        this.mMovieDetailModelContract = movieDetailModelContract;
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
        Timber.d("getMovieDetail %d", movieId);
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
            mMovieDetailViewContract.receivedMovieTrailers(trailers);
        }
    }

    @Override
    public void onGetMovieTrailerFailure(String errorMessage) {
        if(mMovieDetailViewContract != null) {
            mMovieDetailViewContract.failedToGetMovieTrailers(errorMessage);
        }
    }

    @Override
    public void onGetMovieReviewsSuccess(Results<Review> movieReviews) {
        if(mMovieDetailViewContract != null) {
            mMovieDetailViewContract.receivedMovieReviews(movieReviews);
        }
    }

    @Override
    public void onGetMovieReviewsFailure(String errorMessage) {
        if(mMovieDetailViewContract != null) {
            mMovieDetailViewContract.failedToReceiveMovieReviews(errorMessage);
        }
    }

    @Override
    public void requestMovieReviews(int movieId) {
        if(mMovieDetailModelContract != null) {
            mMovieDetailModelContract.getMovieReviews(movieId, MovieDetailPresenterImp.this);
        }
    }

    @Override
    public void requestMovieActors(int movieId) {
        if(mMovieDetailModelContract != null) {
            mMovieDetailModelContract.getMoveActors(movieId, MovieDetailPresenterImp.this);
        }
    }

    @Override
    public void onGetMovieActorsSuccess(Cast<Actor> actorList) {
        mMovieDetailViewContract.successToReceiveMovieActors(actorList);
    }

    @Override
    public void onGetMovieActorsFailure(String errorMessage) {
        mMovieDetailViewContract.failedToReceiveMovieActors(errorMessage);
    }


    @Override
    public void getSimilarMovies(int movieId) {
        mMovieDetailModelContract.getSimilarMovies(movieId, MovieDetailPresenterImp.this);
    }

    @VisibleForTesting
    @Override
    public void onSimilarMovieFailure(String errorMessage) {
        mMovieDetailViewContract.failedToGetSimilarMovies(errorMessage);
    }

    @Override
    public void onSimilarMovieSuccess(Results<Movies> similarMovies) {
        mMovieDetailViewContract.successToGetSimilarMovies(similarMovies);
    }

}
