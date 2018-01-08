package me.androidbox.busbymovies.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import me.androidbox.busbymovies.data.MovieFavouriteModelContract.GetMovieFavourite;
import me.androidbox.busbymovies.data.MovieFavouriteModelContract.InsertListener;
import me.androidbox.busbymovies.data.MovieFavouriteModelContract.RetrieveListener;
import me.androidbox.busbymovies.data.MovieFavouritePresenterContract.MovieFavouriteListListener;
import me.androidbox.busbymovies.models.Movie;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

class MovieFavouritePresenterImpTest {
    private MovieFavouriteModelContract movieFavouriteModelContract;
    private MovieFavouritePresenterContract.DbOperationsListener dbOperationsListener;
    private MovieFavouriteListListener movieFavouriteListListener;
    private MovieFavouritePresenterContract movieFavouritePresenterContract;

    private final int MOVIE_ID = 12345;

    @BeforeEach
    void setup() {
        setupMocks();
        movieFavouritePresenterContract = new MovieFavouritePresenterImp(
                movieFavouriteModelContract,
                dbOperationsListener,
                movieFavouriteListListener);
    }

    @Test
    void testMovieFavouritePresenter_shouldNonNullValue() {
        assertThat(movieFavouritePresenterContract, is(notNullValue()));
    }

    @Test
    void testGetMovieFavourite_doNothing_whenMovieFavouriteModelContract_isNullValue() {
        final GetMovieFavourite getMovieFavourite = mock(GetMovieFavourite.class);
        final MovieFavouritePresenterContract movieFavouritePresenterContract = createPresenterWithNullModel();

        movieFavouritePresenterContract.getMovieFavourite(MOVIE_ID);

        verify(movieFavouriteModelContract, never()).getMovieFavourite(MOVIE_ID, getMovieFavourite);
        verifyNoMoreInteractions(movieFavouriteModelContract);
    }

    @Test
    void testGetMovieFavourite_getMovieFavourite() {
        movieFavouritePresenterContract.getMovieFavourite(MOVIE_ID);

        verify(movieFavouriteModelContract)
                .getMovieFavourite(MOVIE_ID, (GetMovieFavourite)movieFavouritePresenterContract);
        verifyNoMoreInteractions(movieFavouriteModelContract);
    }

    @Test
    void testGetFavouriteMovies_doNothing_whenMovieFavouriteModelContract_isNullValue() {
        final RetrieveListener retrieveListener = mock(RetrieveListener.class);
        final MovieFavouritePresenterContract movieFavouritePresenterContract = createPresenterWithNullModel();

        movieFavouritePresenterContract.getFavouriteMovies();

        verify(movieFavouriteModelContract, never())
                .retrieve(retrieveListener);
        verifyNoMoreInteractions(movieFavouriteModelContract);
    }

    @Test
    void testGetFavouriteMovies_retrieve() {
        movieFavouritePresenterContract.getFavouriteMovies();

        verify(movieFavouriteModelContract)
                .retrieve((RetrieveListener)movieFavouritePresenterContract);
        verifyNoMoreInteractions(movieFavouriteModelContract);
    }

    @Test
    void testInsertFavouriteMovie_insertsMovie() {
        final Movie favourite = new Movie(1234, "poster_path", "overview", "release_date", "title", "backdrop_path", 4.3F, 7.2F);

        movieFavouritePresenterContract.insertFavouriteMovie(favourite);

        verify(movieFavouriteModelContract).insert(favourite, (InsertListener)movieFavouritePresenterContract);
        verifyNoMoreInteractions(movieFavouriteModelContract);
    }

    private MovieFavouritePresenterContract createPresenterWithNullModel() {
                return new MovieFavouritePresenterImp(
                null,
                dbOperationsListener,
                movieFavouriteListListener);
    }

    private void setupMocks() {
        movieFavouriteModelContract = Mockito.mock(MovieFavouriteModelContract.class);
        dbOperationsListener = Mockito.mock(MovieFavouritePresenterContract.DbOperationsListener.class);
        movieFavouriteListListener = Mockito.mock(MovieFavouriteListListener.class);
    }
}