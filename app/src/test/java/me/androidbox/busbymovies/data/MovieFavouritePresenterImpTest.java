package me.androidbox.busbymovies.data;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;

import me.androidbox.busbymovies.data.MovieFavouriteModelContract.GetMovieFavourite;
import me.androidbox.busbymovies.data.MovieFavouriteModelContract.RetrieveListener;
import me.androidbox.busbymovies.data.MovieFavouritePresenterContract.MovieFavouriteListListener;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class MovieFavouritePresenterImpTest {
    private MovieFavouriteModelContract movieFavouriteModelContract;
    private MovieFavouritePresenterContract.DbOperationsListener dbOperationsListener;
    private MovieFavouriteListListener movieFavouriteListListener;
    private MovieFavouritePresenterContract movieFavouritePresenterContract;

    private final int MOVIE_ID = 12345;

    @Before
    public void setup() {
        setupMocks();
        movieFavouritePresenterContract = new MovieFavouritePresenterImp(
                movieFavouriteModelContract,
                dbOperationsListener,
                movieFavouriteListListener);
    }

    @Test
    public void testMovieFavouritePresenter_shouldNonNullValue() {
        assertThat(movieFavouritePresenterContract, is(notNullValue()));
    }

    @Test
    public void testGetMovieFavourite_doNothing_whenMovieFavouriteModelContract_equalsNull() {
        final GetMovieFavourite getMovieFavourite = mock(GetMovieFavourite.class);
        final MovieFavouritePresenterContract movieFavouritePresenterContract = new MovieFavouritePresenterImp(
                null,
                dbOperationsListener,
                movieFavouriteListListener);

        movieFavouritePresenterContract.getMovieFavourite(MOVIE_ID);

        verify(movieFavouriteModelContract, never()).getMovieFavourite(MOVIE_ID, getMovieFavourite);
        verifyNoMoreInteractions(movieFavouriteModelContract);
    }

    @Test
    public void testGetMovieFavourite_getMovieFavourite() {
        final GetMovieFavourite getMovieFavourite = mock(GetMovieFavourite.class);

        movieFavouritePresenterContract.getMovieFavourite(MOVIE_ID);

        verify(movieFavouriteModelContract).getMovieFavourite(MOVIE_ID, getMovieFavourite);
        verifyNoMoreInteractions(movieFavouriteModelContract);
    }

    @Ignore
    @Test
    public void testGetFavouriteMovies_retrieve() {
        final RetrieveListener retrieveListener = mock(RetrieveListener.class);

        movieFavouritePresenterContract.getFavouriteMovies();

        verify(movieFavouriteModelContract).retrieve(retrieveListener);
        verifyNoMoreInteractions(movieFavouriteModelContract);
    }

    private void setupMocks() {
        movieFavouriteModelContract = Mockito.mock(MovieFavouriteModelContract.class);
        dbOperationsListener = Mockito.mock(MovieFavouritePresenterContract.DbOperationsListener.class);
        movieFavouriteListListener = Mockito.mock(MovieFavouriteListListener.class);
    }
}