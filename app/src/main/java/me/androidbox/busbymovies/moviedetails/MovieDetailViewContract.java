package me.androidbox.busbymovies.moviedetails;

import me.androidbox.busbymovies.models.Movie;

/**
 * Created by steve on 3/2/17.
 */

public interface MovieDetailViewContract {
    void displayMovieDetails(Movie movie);
    void displayErrorFailedToGetMovie(String errMessage);
}
