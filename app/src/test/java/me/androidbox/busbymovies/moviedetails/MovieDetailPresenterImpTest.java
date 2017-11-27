package me.androidbox.busbymovies.moviedetails;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import me.androidbox.busbymovies.models.Movies;
import me.androidbox.busbymovies.models.Results;

import static org.mockito.Mockito.verify;

/**
 * Created by steve on 10/7/17.
 */
@RunWith(MockitoJUnitRunner.class)
public class MovieDetailPresenterImpTest {
    private MovieDetailPresenterImp movieDetailPresenter;

    @Mock MovieDetailViewContract movieDetailViewContract;
    @Mock MovieDetailModelContract movieDetailModelContract;

    @Before
    public void setup() {
        movieDetailPresenter = new MovieDetailPresenterImp(movieDetailModelContract);
        movieDetailPresenter.attachView(movieDetailViewContract);
    }

    @Test
    public void testGetSimilarMovies() {
        final int movieId = 12345;

        movieDetailPresenter.getSimilarMovies(movieId);

        verify(movieDetailModelContract).getSimilarMovies(movieId, movieDetailPresenter);
    }

    @Test
    public void testOnSimilarMovieFailure_isCalledWhenOnFailure() {
        final String errorMessage = "Error Message";

        movieDetailPresenter.onSimilarMovieFailure(errorMessage);

        verify(movieDetailViewContract).failedToGetSimilarMovies(errorMessage);
    }

    @Test
    public void testOnSimilarMovieSuccess_isCallWhenOnSuccess() {
        final Results<Movies> moviesResults = new Results<>();

        movieDetailPresenter.onSimilarMovieSuccess(moviesResults);

        verify(movieDetailViewContract).successToGetSimilarMovies(moviesResults);
    }
}