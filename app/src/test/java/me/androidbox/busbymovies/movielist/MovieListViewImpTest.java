package me.androidbox.busbymovies.movielist;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import me.androidbox.busbymovies.data.MovieFavouritePresenterContract;

import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

/**
 * Created by steve on 12/27/17.
 */
@RunWith(MockitoJUnitRunner.class)
public class MovieListViewImpTest {
    private MovieListViewImp movieListViewImp;

    @Mock
    private MovieListPresenterContract movieListPresenter;
    @Mock
    private MovieFavouritePresenterContract movieFavouritePresenter;

    @Before
    public void setup() {
        movieListViewImp = MovieListViewImp.newInstance();
        movieListViewImp.mMovieListPresenterImp = movieListPresenter;
        movieListViewImp.mMovieFavouritePresenterImp = movieFavouritePresenter;
    }

    @Test
    public void testMovieListViewImp_isNotNullValue() {
        assertThat(movieListViewImp, is(notNullValue()));
    }

    @Test
    public void testGetPopular_getsPopularMovies() {
        movieListViewImp.getPopular();

        verify(movieListPresenter).getPopularMovies();
        verify(movieListPresenter).closeSortFab();
        verifyNoMoreInteractions(movieListPresenter);
    }

    @Test
    public void testGetTopRated_getsTopRated() {
        movieListViewImp.getTopRated();

        verify(movieListPresenter).getTopRatedMovies();
        verify(movieListPresenter).closeSortFab();
        verifyNoMoreInteractions(movieListPresenter);
    }

    @Test
    public void testGetFavourites_getsFavourites() {
        movieListViewImp.getFavourites();

        verify(movieFavouritePresenter).getFavouriteMovies(movieListViewImp);
        verify(movieListPresenter).closeSortFab();
        verifyNoMoreInteractions(movieFavouritePresenter);
        verifyNoMoreInteractions(movieListPresenter);
    }
}