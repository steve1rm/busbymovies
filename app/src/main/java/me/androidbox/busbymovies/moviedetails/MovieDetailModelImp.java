package me.androidbox.busbymovies.moviedetails;

/**
 * Created by steve on 3/2/17.
 */

public interface MovieDetailModelImp implements MovieDetailModelContract {

    @Override
    default void getMovieDetail(int movieId, GetMovieDetailListener getMovieDetailListener) {

    }
}
