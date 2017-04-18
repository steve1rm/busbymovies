package me.androidbox.busbymovies.moviedetails;

import me.androidbox.busbymovies.models.Movie;
import me.androidbox.busbymovies.models.Results;
import me.androidbox.busbymovies.models.Review;
import me.androidbox.busbymovies.models.Trailer;

/**
 * Created by steve on 3/2/17.
 */

public interface MovieDetailModelContract {
    interface GetMovieDetailListener {
        void onGetMovieDetailFailure(String errMessage);
        void onGetMovieDetailSuccess(Movie movie);
    }
    void getMovieDetail(int movieId, GetMovieDetailListener getMovieDetailListener);

    interface GetMovieTrailerListener {
        void onGetMovieTrailerSuccess(Results<Trailer> trailers);
        void onGetMovieTrailerFailure(String errorMessage);
    }
    void getMovieTrailer(int movieId, GetMovieTrailerListener getMovieTrailerListener);

    interface MovieReviewsListener {
        void onGetMovieReviewsSuccess(Results<Review> movieReviews);
        void onGetMovieReviewsFailure(String errorMessage);
    }
    void getMovieReviews(int movieId, MovieReviewsListener movieReviewsListener);

    void releaseResources();
}
