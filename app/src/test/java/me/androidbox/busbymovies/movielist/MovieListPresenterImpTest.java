package me.androidbox.busbymovies.movielist;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

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
@RunWith(MockitoJUnitRunner.class)
public class MovieListPresenterImpTest {

    @Mock
    private MovieListModelContract mockMovieListModelContract;
    @Mock
    private MovieListViewContract mockMovieListViewContract;

    private MovieListPresenterImp movieListPresenterContract;

    @Before
    public void setUp() throws Exception {
        movieListPresenterContract = new MovieListPresenterImp(mockMovieListModelContract);
        movieListPresenterContract.attachView(mockMovieListViewContract);
    }

    @Ignore
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


}