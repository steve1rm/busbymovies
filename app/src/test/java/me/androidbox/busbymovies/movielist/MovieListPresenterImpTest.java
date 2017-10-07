package me.androidbox.busbymovies.movielist;

import org.junit.Before;
import org.junit.Test;

import me.androidbox.busbymovies.models.Movies;
import me.androidbox.busbymovies.models.Results;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by steve on 2/22/17.
 */
public class MovieListPresenterImpTest {

    private MovieListModelContract mockMovieListModelContract;
    private MovieListViewContract mockMovieListViewContract;
    private MovieListPresenterImp movieListPresenterContract;

    @Before
    public void setUp() throws Exception {
        mockMovieListModelContract = mock(MovieListModelContract.class);
        mockMovieListViewContract = mock(MovieListViewContract.class);

        movieListPresenterContract = new MovieListPresenterImp(mockMovieListModelContract);
        movieListPresenterContract.attachView(mockMovieListViewContract);
    }

    @Test(expected = NullPointerException.class)
    public void shouldFailToAttachViewIfTheViewIsNull() {
        movieListPresenterContract.attachView(null);
    }

    @Test
    public void shouldReleaseModelResourcesWhenDetached() {
        doNothing().when(mockMovieListModelContract).releaseResources();

        movieListPresenterContract.detachView();

        verify(mockMovieListModelContract, times(1)).releaseResources();
    }

    @Test
    public void testSearchMoviesOnSuccess() {
        final String MOVIE_NAME = "movie name";
        final int MOVIE_YEAR = 2002;

        movieListPresenterContract.searchMovies(MOVIE_NAME, MOVIE_YEAR);

        verify(mockMovieListModelContract).searchForMovies(eq(MOVIE_NAME), eq(MOVIE_YEAR), any());
    }

    @Test
    public void testSearchMovieOnSuccess() {
        final Results<Movies> movies = new Results<>();

        movieListPresenterContract.onSearchSuccess(movies);

        verify(mockMovieListViewContract).successToGetSearchMovies(movies);
    }

    @Test
    public void testSearchMovieOnFailureWhenFailedToGetMovies() {
        final String ERROR_MESSAGE = "error_message";

        movieListPresenterContract.onSearchFailure(ERROR_MESSAGE);

        verify(mockMovieListViewContract).failedToGetSearchMovies(ERROR_MESSAGE);
    }

    @Test
    public void testGetSimilarMovies() {
        final int movieId = 12345;

        movieListPresenterContract.getSimilarMovies(movieId);

        verify(mockMovieListModelContract).getSimilarMovies(movieId, movieListPresenterContract);
    }

    @Test
    public void testOnSimilarMovieFailure_isCalledWhenOnFailure() {
        final String errorMessage = "Error Message";

        movieListPresenterContract.onSimilarMovieFailure(errorMessage);

        verify(mockMovieListViewContract).failedToGetSimilarMovies(errorMessage);
    }

    @Test
    public void testOnSimilarMovieSuccess_isCallWhenOnSuccess() {
        final Results<Movies> moviesResults = new Results<>();

        movieListPresenterContract.onSimilarMovieSuccess(moviesResults);

        verify(mockMovieListViewContract).successToGetSimilarMovies(moviesResults);
    }
}