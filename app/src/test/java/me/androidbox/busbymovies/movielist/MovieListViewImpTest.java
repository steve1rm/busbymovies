package me.androidbox.busbymovies.movielist;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

/**
 * Created by steve on 12/27/17.
 */
@RunWith(MockitoJUnitRunner.class)
public class MovieListViewImpTest {
    private MovieListViewImp movieListViewImp;

    @Mock MovieListPresenterContract movieListPresenter;

    @Before
    public void setup() {
        movieListViewImp = MovieListViewImp.newInstance();
        movieListViewImp.mMovieListPresenterImp = movieListPresenter;
    }

    @Test
    public void testMovieListViewImp_isNotNullValue() {
        assertThat(movieListViewImp, is(notNullValue()));
    }

    @Test
    public void testGetPopular_getPopularMovies() {
        movieListViewImp.getPopular();

        verify(movieListPresenter).getPopularMovies();
        verify(movieListPresenter).closeSortFab();
        verifyNoMoreInteractions(movieListPresenter);
    }
}