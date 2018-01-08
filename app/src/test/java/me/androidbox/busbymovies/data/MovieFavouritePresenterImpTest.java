package me.androidbox.busbymovies.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import me.androidbox.busbymovies.data.MovieFavouriteModelContract.DeleteListener;
import me.androidbox.busbymovies.data.MovieFavouriteModelContract.GetMovieFavourite;
import me.androidbox.busbymovies.data.MovieFavouriteModelContract.InsertListener;
import me.androidbox.busbymovies.data.MovieFavouriteModelContract.QueryMovieListener;
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

    @Nested
    @DisplayName("Running testing using MovieFavouriteModelContract")
    class WhenNew {
        @Test
        @DisplayName("The presenter should not be a null value")
        void testMovieFavouritePresenter_shouldNonNullValue() {
            assertThat(movieFavouritePresenterContract, is(notNullValue()));
        }

        @Test
        @DisplayName("Do nothing if model contract is null when getting favourite movies")
        void testGetMovieFavourite_doNothing_whenMovieFavouriteModelContract_isNullValue() {
            final GetMovieFavourite getMovieFavourite = mock(GetMovieFavourite.class);
            final MovieFavouritePresenterContract movieFavouritePresenterContract = createPresenterWithNullModel();

            movieFavouritePresenterContract.getMovieFavourite(MOVIE_ID);

            verify(movieFavouriteModelContract, never()).getMovieFavourite(MOVIE_ID, getMovieFavourite);
            verifyNoMoreInteractions(movieFavouriteModelContract);
        }

        @Test
        @DisplayName("Retrieves all movies favourites")
        void testGetMovieFavourite_getMovieFavourite() {
            movieFavouritePresenterContract.getMovieFavourite(MOVIE_ID);

            verify(movieFavouriteModelContract)
                    .getMovieFavourite(MOVIE_ID, (GetMovieFavourite)movieFavouritePresenterContract);
            verifyNoMoreInteractions(movieFavouriteModelContract);
        }

        @Test
        @DisplayName("If the model contract is null do nothing")
        void testGetFavouriteMovies_doNothing_whenMovieFavouriteModelContract_isNullValue() {
            final RetrieveListener retrieveListener = mock(RetrieveListener.class);
            final MovieFavouritePresenterContract movieFavouritePresenterContract
                    = createPresenterWithNullModel();

            movieFavouritePresenterContract.getFavouriteMovies();

            verify(movieFavouriteModelContract, never())
                    .retrieve(retrieveListener);
            verifyNoMoreInteractions(movieFavouriteModelContract);
        }

        @Test
        @DisplayName("Retrieve all movie favourites")
        void testGetFavouriteMovies_retrieve() {
            movieFavouritePresenterContract.getFavouriteMovies();

            verify(movieFavouriteModelContract)
                    .retrieve((RetrieveListener)movieFavouritePresenterContract);
            verifyNoMoreInteractions(movieFavouriteModelContract);
        }

        @Test
        @DisplayName("Insert a new movie favourite")
        void testInsertFavouriteMovie_insertsMovie() {
            final Movie movie = createFavouriteMovie();

            movieFavouritePresenterContract.insertFavouriteMovie(movie);

            verify(movieFavouriteModelContract)
                    .insert(movie, (InsertListener)movieFavouritePresenterContract);
            verifyNoMoreInteractions(movieFavouriteModelContract);
        }

        @Test
        @DisplayName("Do not insert movie favourite is model if null value")
        void testInsertFavouriteMovie_doNotInsertMovie() {
            final Movie movie = createFavouriteMovie();
            final InsertListener insertListener = mock(InsertListener.class);
            final MovieFavouritePresenterContract movieFavouritePresenterContract
                    = createPresenterWithNullModel();

            movieFavouritePresenterContract.insertFavouriteMovie(movie);

            verify(movieFavouriteModelContract, never()).insert(movie, insertListener);
            verifyNoMoreInteractions(movieFavouriteModelContract);
        }

        @Test
        @DisplayName("Do not delete movie favourite if model has a null value")
        void testDeleteFavouriteMovie_doNotDeleteMovie() {
            final MovieFavouritePresenterContract movieFavouritePresenterContract
                    = createPresenterWithNullModel();

            movieFavouritePresenterContract.deleteFavouriteMovie(MOVIE_ID);

            verify(movieFavouriteModelContract, never())
                    .delete(MOVIE_ID, (DeleteListener)movieFavouritePresenterContract);
            verifyNoMoreInteractions(movieFavouriteModelContract);
        }

        @Test
        @DisplayName("Delete favourite movie")
        void testDeleteFavouriteMovie_movieDeleted() {
            movieFavouritePresenterContract.deleteFavouriteMovie(MOVIE_ID);

            verify(movieFavouriteModelContract)
                    .delete(MOVIE_ID, (DeleteListener)movieFavouritePresenterContract);
            verifyNoMoreInteractions(movieFavouriteModelContract);
        }

        @Test
        @DisplayName("Do not query favourite movies if model has a null value")
        void testHasMovieAsFavourite_doNotQueryMovie() {
            final MovieFavouritePresenterContract movieFavouritePresenterContract
                    = createPresenterWithNullModel();

            movieFavouritePresenterContract.hasMovieAsFavourite(MOVIE_ID);

            verify(movieFavouriteModelContract, never())
                    .queryMovie(MOVIE_ID, (QueryMovieListener)movieFavouritePresenterContract);
            verifyNoMoreInteractions(movieFavouriteModelContract);
        }

        @Test
        @DisplayName("Query favourite movie to see if it exists")
        void testHasMovieAsFavourite_queryMovie() {
            movieFavouritePresenterContract.hasMovieAsFavourite(MOVIE_ID);

            verify(movieFavouriteModelContract)
                    .queryMovie(MOVIE_ID, (QueryMovieListener)movieFavouritePresenterContract);
            verifyNoMoreInteractions(movieFavouriteModelContract);
        }

        private Movie createFavouriteMovie() {
            return new Movie(
                    1234,
                    "poster_path",
                    "overview",
                    "release_date",
                    "title",
                    "backdrop_path",
                    4.3F,
                    7.2F);
        }

        private MovieFavouritePresenterContract createPresenterWithNullModel() {
            return new MovieFavouritePresenterImp(
                    null,
                    dbOperationsListener,
                    movieFavouriteListListener);
        }
    }

    private void setupMocks() {
        movieFavouriteModelContract = mock(MovieFavouriteModelContract.class);
        dbOperationsListener = mock(MovieFavouritePresenterContract.DbOperationsListener.class);
        movieFavouriteListListener = mock(MovieFavouriteListListener.class);
    }
}