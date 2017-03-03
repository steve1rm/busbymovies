package me.androidbox.busbymovies.moviedetails;

/**
 * Created by steve on 3/2/17.
 */

public interface MovieDetailPresenterContract<MovieDetailView> {
    void attachView(MovieDetailView movieDetailView);
    void detachView();

    void getMovieDetail(int movieId);
    float getVoteAverage(float voteAverage);
}
