package me.androidbox.busbymovies.moviedetails;

import me.androidbox.busbymovies.models.Actor;
import me.androidbox.busbymovies.models.Cast;
import me.androidbox.busbymovies.models.Movie;
import me.androidbox.busbymovies.models.Movies;
import me.androidbox.busbymovies.models.Results;
import me.androidbox.busbymovies.models.Review;
import me.androidbox.busbymovies.models.Trailer;

/**
 * Created by steve on 3/2/17.
 */

public interface MovieDetailViewContract {
    void displayMovieDetails(Movie movie);
    void displayErrorFailedToGetMovie(String errMessage);
    void receivedMovieTrailers(Results<Trailer> trailerList);
    void failedToGetMovieTrailers(String errorMessage);
    void receivedMovieReviews(Results<Review> reviews);
    void failedToReceiveMovieReviews(String errorMessage);
    void failedToReceiveMovieActors(final String errorMessage);
    void successToReceiveMovieActors(final Cast<Actor> actorList);
    void failedToGetSimilarMovies(final String errorMessage);
    void successToGetSimilarMovies(final Results<Movies> similarMovies);
}
