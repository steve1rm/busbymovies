package me.androidbox.busbymovies.movielist;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import me.androidbox.busbymovies.models.Results;

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
    private MovieListPresenterContract<MovieListViewContract> movieListPresenterContract;
    private Results movies;

    @Before
    public void setUp() throws Exception {
        mockMovieListModelContract = mock(MovieListModelContract.class);
        mockMovieListViewContract = mock(MovieListViewContract.class);

        movieListPresenterContract = new MovieListPresenterImp(mockMovieListModelContract);
    }

    @Test
    public void shouldFailToAttachViewIfTheViewIsNull() {
        movieListPresenterContract.attachView(null);
        movieListPresenterContract.getPopularMovies();

        verify(mockMovieListModelContract, times(0)).getPopularMovies(null);
    }

    @Test
    public void shouldReleaseModelResourcesWhenDetached() {
        doNothing().when(mockMovieListModelContract).releaseResources();

        movieListPresenterContract.detachView();

        verify(mockMovieListModelContract, times(1)).releaseResources();
    }

    @Test
    public void shouldAttachViewWhenViewIsNotNull() {
/*
        movieListPresenterContract.attachView(mockMovieListViewContract);
        movieListPresenterContract.getPopularMovies();
        MovieListModelContract.PopularMovieResultsListener mockPopularMoviesResultsListener =
                Mockito.mock(MovieListModelContract.PopularMovieResultsListener.class);

        verify(mockMovieListModelContract, times(1)).getPopularMovies(null);
*/
    }

    @After
    public void tearDown() throws Exception {

    }

}