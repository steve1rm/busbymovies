package me.androidbox.busbymovies.movielist;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import me.androidbox.busbymovies.models.Movie;
import me.androidbox.busbymovies.models.Results;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyObject;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

        movieListPresenterContract = new MovieListPresenterImp();
    }

    @Test
    public void shouldFailToAttachViewIfTheViewIsNull() {
        movieListPresenterContract.attachView(null);
        movieListPresenterContract.getPopularMovies();

        verify(mockMovieListModelContract, times(0)).getPopularMovies(null);
    }

    @Test
    public void shouldAttachViewWhenViewIsNotNull() {
        movieListPresenterContract.attachView(mockMovieListViewContract);
        movieListPresenterContract.getPopularMovies();

        verify(mockMovieListModelContract, times(1)).getPopularMovies(null);
    }

    @After
    public void tearDown() throws Exception {

    }

}