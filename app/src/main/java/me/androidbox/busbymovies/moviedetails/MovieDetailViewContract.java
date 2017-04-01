package me.androidbox.busbymovies.moviedetails;

import me.androidbox.busbymovies.models.Movie;
import me.androidbox.busbymovies.models.Results;
import me.androidbox.busbymovies.models.Trailer;

/**
 * Created by steve on 3/2/17.
 */

public interface MovieDetailViewContract {
    void displayMovieDetails(Movie movie);
    void displayErrorFailedToGetMovie(String errMessage);
    void startPlayingMovieTrailer(Results<Trailer> trailerList);
    void failedToGetMovieTrailers(String errorMessage);
}
