package me.androidbox.busbymovies.moviedetails;

/**
 * Created by steve on 3/2/17.
 */

public interface MovieDetailPresenterContract<MovieDetailView> {
    void attachView(MovieDetailView movieDetailView);
    void detachView();

    void getMovieDetail(int movieId);
    float getVoteAverage(float voteAverage);
    String getMovieFormattedDate(String date, String format);
    void requestMovieTrailer(int movieId);
    void requestMovieReviews(int movieId);
    void requestMovieActors(final int movieId);
    void getSimilarMovies(final int movieId);
}
