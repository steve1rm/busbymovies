package me.androidbox.busbymovies.data;

import org.junit.Ignore;

import me.androidbox.busbymovies.data.MovieFavouritePresenterContract.MovieFavouriteListListener;

@Ignore
class MovieFavouritePresenterImpTest {
    private MovieFavouriteModelContract movieFavouriteModelContract;
    private MovieFavouritePresenterContract.DbOperationsListener dbOperationsListener;
    private MovieFavouriteListListener movieFavouriteListListener;

    private final int MOVIE_ID = 12345;
/*
    @BeforeEach
    void setup() {
        setupMocks();
    }

    @Nested
    @DisplayName("Running testing using MovieFavouriteModelContract")
    class FavouriteModelContract {
        @Test
        @DisplayName("The presenter should not be a null value")
        void testMovieFavouritePresenter_shouldNonNullValue() {
            assertNotNull(createPresenterModel());
            assertNotNull(createPresenterWithNullModel());
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
            final MovieFavouritePresenterContract movieFavouritePresenterContract
                    = createPresenterModel();

            movieFavouritePresenterContract.getMovieFavourite(MOVIE_ID);

            verify(movieFavouriteModelContract)
                    .getMovieFavourite(MOVIE_ID, (GetMovieFavourite) movieFavouritePresenterContract);
            verifyNoMoreInteractions(movieFavouriteModelContract);
        }

        @Test
        @DisplayName("If the model contract is null do nothing")
        void testGetFavouriteMovies_doNothing_whenMovieFavouriteModelContract_isNullValue() {
            final RetrieveListener retrieveListener = mock(RetrieveListener.class);
            final MovieFavouritePresenterContract movieFavouritePresenterContract
                    = createPresenterWithNullModel();

            movieFavouritePresenterContract.getFavouriteMovies();

            verify(movieFavouriteModelContract, never()).retrieve(retrieveListener);
            verifyNoMoreInteractions(movieFavouriteModelContract);
        }

        @Test
        @DisplayName("Retrieve all movie favourites")
        void testGetFavouriteMovies_retrieve() {
            final MovieFavouritePresenterContract movieFavouritePresenterContract
                    = createPresenterModel();

            movieFavouritePresenterContract.getFavouriteMovies();

            verify(movieFavouriteModelContract)
                    .retrieve((RetrieveListener) movieFavouritePresenterContract);
            verifyNoMoreInteractions(movieFavouriteModelContract);
        }

        @Test
        @DisplayName("Insert a new movie favourite")
        void testInsertFavouriteMovie_insertsMovie() {
            final Movie movie = createFavouriteMovie();
            final MovieFavouritePresenterContract movieFavouritePresenterContract
                    = createPresenterModel();

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
            final DeleteListener deleteListener = mock(DeleteListener.class);

            movieFavouritePresenterContract.deleteFavouriteMovie(MOVIE_ID);

            verify(movieFavouriteModelContract, never())
                    .delete(MOVIE_ID, deleteListener);
            verifyNoMoreInteractions(movieFavouriteModelContract);
        }

        @Test
        @DisplayName("Delete favourite movie")
        void testDeleteFavouriteMovie_movieDeleted() {
            final MovieFavouritePresenterContract movieFavouritePresenterContract
                    = createPresenterModel();

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
            final QueryMovieListener queryMovieListener = mock(QueryMovieListener.class);

            movieFavouritePresenterContract.hasMovieAsFavourite(MOVIE_ID);

            verify(movieFavouriteModelContract, never())
                    .queryMovie(MOVIE_ID, queryMovieListener);
            verifyNoMoreInteractions(movieFavouriteModelContract);
        }

        @Test
        @DisplayName("Query favourite movie to see if it exists")
        void testHasMovieAsFavourite_queryMovie() {
            final MovieFavouritePresenterContract movieFavouritePresenterContract = createPresenterModel();
            movieFavouritePresenterContract.hasMovieAsFavourite(MOVIE_ID);

            verify(movieFavouriteModelContract)
                    .queryMovie(MOVIE_ID, (QueryMovieListener)movieFavouritePresenterContract);
            verifyNoMoreInteractions(movieFavouriteModelContract);
        }

        private MovieFavouritePresenterContract createPresenterWithNullModel() {
            return new MovieFavouritePresenterImp(
                    null,
                    dbOperationsListener,
                    movieFavouriteListListener);
        }

        private MovieFavouritePresenterContract createPresenterModel() {
            return new MovieFavouritePresenterImp(
                    movieFavouriteModelContract,
                    dbOperationsListener,
                    movieFavouriteListListener);
        }
    }

    @Nested
    @DisplayName("Testing db operations")
    class DbOperations {
        private MovieFavouriteModelContract.InsertListener movieFavouritePresenterContract;
        private final String ERROR_MESSAGE = "error_message";

        @BeforeEach
        void setup() {
            movieFavouritePresenterContract = new MovieFavouritePresenterImp(
                    movieFavouriteModelContract,
                    dbOperationsListener,
                    movieFavouriteListListener);
        }

        @Test
        @DisplayName("Do not insert when db model is null")
        void testOnInsertFailed_doNothing_onInsertFavouriteFailure() {
            movieFavouritePresenterContract = createInsertListenerWithNullModel();

            movieFavouritePresenterContract.onInsertFailed(ERROR_MESSAGE);

            verify(dbOperationsListener, never()).onInsertFavouriteFailure(ERROR_MESSAGE);
            verifyNoMoreInteractions(dbOperationsListener);
        }

        @Test
        @DisplayName("Display error message when insertion fails")
        void testOnInsertFailed_showErrorMessage() {
            movieFavouritePresenterContract = createInsertListenerModel();

            movieFavouritePresenterContract.onInsertFailed(ERROR_MESSAGE);

            verify(dbOperationsListener).onInsertFavouriteFailure(ERROR_MESSAGE);
            verifyNoMoreInteractions(dbOperationsListener);
        }

        @Test
        @DisplayName("Do not insert if db operations is null")
        void testOnInsertSuccess_doNotDisplaySuccess() {
            movieFavouritePresenterContract = createInsertListenerWithNullModel();

            movieFavouritePresenterContract.onInsertSuccess();

            verify(dbOperationsListener, never()).onInsertFavouriteSuccess();
            verifyNoMoreInteractions(dbOperationsListener);
        }

        @Test
        @DisplayName("Display success on successful insert")
        void testOnInsertSuccess_onInsertFavouriteSuccess() {
            movieFavouritePresenterContract = createInsertListenerModel();

            movieFavouritePresenterContract.onInsertSuccess();

            verify(dbOperationsListener).onInsertFavouriteSuccess();
            verifyNoMoreInteractions(dbOperationsListener);
        }

        @Test
        @DisplayName("Do not display error message when db is null")
        void testOnDeleteFailed_doNotDisplayMessage() {
            final MovieFavouriteModelContract.DeleteListener deleteListener = new MovieFavouritePresenterImp(
                    movieFavouriteModelContract,
                    null,
                    movieFavouriteListListener);

            deleteListener.onDeleteFailed(ERROR_MESSAGE);

            verify(dbOperationsListener, never()).onDeleteFavouriteMovieFailure(ERROR_MESSAGE);
            verifyNoMoreInteractions(dbOperationsListener);
        }

        @Test
        @DisplayName("Do display error message when db is null")
        void testOnDeleteFailed_displayMessage() {
            final MovieFavouriteModelContract.DeleteListener deleteListener = new MovieFavouritePresenterImp(
                    movieFavouriteModelContract,
                    dbOperationsListener,
                    movieFavouriteListListener);

            deleteListener.onDeleteFailed(ERROR_MESSAGE);

            verify(dbOperationsListener).onDeleteFavouriteMovieFailure(ERROR_MESSAGE);
            verifyNoMoreInteractions(dbOperationsListener);
        }

        @Test
        @DisplayName("Do not display error message when db is null for deleting")
        void testOnDeleteSuccess_doNotDisplayMessage() {
            final MovieFavouriteModelContract.DeleteListener deleteListener = new MovieFavouritePresenterImp(
                    movieFavouriteModelContract,
                    null,
                    movieFavouriteListListener);

            deleteListener.onDeleteSuccess(1);

            verify(dbOperationsListener, never()).onDeleteFavouriteMovieSuccess(1);
            verifyNoMoreInteractions(dbOperationsListener);
        }

        @Test
        @DisplayName("Do display error message when db is null for deleting")
        void testOnDeleteSuccess_displayMessage() {
            final MovieFavouriteModelContract.DeleteListener deleteListener = new MovieFavouritePresenterImp(
                    movieFavouriteModelContract,
                    dbOperationsListener,
                    movieFavouriteListListener);

            deleteListener.onDeleteSuccess(1);

            verify(dbOperationsListener).onDeleteFavouriteMovieSuccess(1);
            verifyNoMoreInteractions(dbOperationsListener);
        }

        @Test
        @DisplayName("Do not display error message when db is null for querying")
        void testOnQueryMovieFailed_doNotDisplayMessage() {
            final MovieFavouriteModelContract.QueryMovieListener queryMovieListener = new MovieFavouritePresenterImp(
                    movieFavouriteModelContract,
                    null,
                    movieFavouriteListListener);

            queryMovieListener.onQueryMovieFailed(ERROR_MESSAGE);

            verify(dbOperationsListener, never()).onHasMovieFavouriteFailure(ERROR_MESSAGE);
            verifyNoMoreInteractions(dbOperationsListener);
        }

        @Test
        @DisplayName("Do not display error message when db is null for querying")
        void testOnQueryMovieFailed_doDisplayMessage() {
            final MovieFavouriteModelContract.QueryMovieListener queryMovieListener = new MovieFavouritePresenterImp(
                    movieFavouriteModelContract,
                    dbOperationsListener,
                    movieFavouriteListListener);

            queryMovieListener.onQueryMovieFailed(ERROR_MESSAGE);

            verify(dbOperationsListener).onHasMovieFavouriteFailure(ERROR_MESSAGE);
            verifyNoMoreInteractions(dbOperationsListener);
        }

        @Test
        @DisplayName("Do display error message when db is null for querying")
        void testOnQueryMovieSuccess_displayMessage() {
            final MovieFavouriteModelContract.QueryMovieListener queryMovieListener = new MovieFavouritePresenterImp(
                    movieFavouriteModelContract,
                    dbOperationsListener,
                    movieFavouriteListListener);

            queryMovieListener.onQueryMovieSuccess(12345, true);

            verify(dbOperationsListener).onHasMovieFavouriteSuccess(12345, true);
            verifyNoMoreInteractions(dbOperationsListener);
        }

        @Test
        @DisplayName("Do display error message when db is null for querying")
        void testOnQueryMovieSuccess_doNotDisplayMessage() {
            final MovieFavouriteModelContract.QueryMovieListener queryMovieListener = new MovieFavouritePresenterImp(
                    movieFavouriteModelContract,
                    null,
                    movieFavouriteListListener);

            queryMovieListener.onQueryMovieSuccess(12345, true);

            verify(dbOperationsListener, never()).onHasMovieFavouriteSuccess(12345, true);
            verifyNoMoreInteractions(dbOperationsListener);
        }


        @Test
        @DisplayName("Do not display error message when db is null for getting movie")
        void testOnGetMovieFavourite_doNotDisplayMessage() {
            final MovieFavouriteModelContract.GetMovieFavourite getMovieFavourite = new MovieFavouritePresenterImp(
                    movieFavouriteModelContract,
                    null,
                    movieFavouriteListListener);

            getMovieFavourite.onGetMovieFavouriteFailure(ERROR_MESSAGE);

            verify(dbOperationsListener, never()).onGetMovieFavouriteFailure(ERROR_MESSAGE);
            verifyNoMoreInteractions(dbOperationsListener);
        }

        @Test
        @DisplayName("Do not display error message when db is null for getting movie")
        void testOnGetMovieFavourite_doDisplayMessage() {
            final MovieFavouriteModelContract.GetMovieFavourite getMovieFavourite = new MovieFavouritePresenterImp(
                    movieFavouriteModelContract,
                    dbOperationsListener,
                    movieFavouriteListListener);

            getMovieFavourite.onGetMovieFavouriteFailure(ERROR_MESSAGE);

            verify(dbOperationsListener).onGetMovieFavouriteFailure(ERROR_MESSAGE);
            verifyNoMoreInteractions(dbOperationsListener);
        }

        @Test
        @DisplayName("Do display error message when db is null for getting movie")
        void testOnGetMovieFavourite_displayMessage() {
            final MovieFavouriteModelContract.GetMovieFavourite getMovieFavourite = new MovieFavouritePresenterImp(
                    movieFavouriteModelContract,
                    dbOperationsListener,
                    movieFavouriteListListener);
            final Movie favourite = createFavouriteMovie();

            getMovieFavourite.onGetMovieFavouriteSuccess(favourite);

            verify(dbOperationsListener).onGetMovieFavouriteSuccess(favourite);
            verifyNoMoreInteractions(dbOperationsListener);
        }

        @Test
        @DisplayName("Do display error message when db is null for getting movie")
        void testOnGetMovieFavourite_doNotGetFavouriteMovie() {
            final MovieFavouriteModelContract.GetMovieFavourite getMovieFavourite = new MovieFavouritePresenterImp(
                    movieFavouriteModelContract,
                    null,
                    movieFavouriteListListener);
            final Movie favourite = createFavouriteMovie();

            getMovieFavourite.onGetMovieFavouriteSuccess(favourite);

            verify(dbOperationsListener, never()).onGetMovieFavouriteSuccess(favourite);
            verifyNoMoreInteractions(dbOperationsListener);
        }


        @Test
        @DisplayName("Do not display error message when db is null for getting movie")
        void testOnRetrieve_doDisplayMessage() {
            final MovieFavouriteModelContract.RetrieveListener retrieveListener = new MovieFavouritePresenterImp(
                    movieFavouriteModelContract,
                    dbOperationsListener,
                    movieFavouriteListListener);

            retrieveListener.onRetrieveFailed(ERROR_MESSAGE);

            verify(movieFavouriteListListener).onGetFavouriteMoviesFailure(ERROR_MESSAGE);
            verifyNoMoreInteractions(movieFavouriteListListener);
        }

        @Test
        @DisplayName("Do not display error message when db is null for getting movie")
        void testOnRetrieve_doNotDisplayMessageError() {
            final MovieFavouriteModelContract.RetrieveListener retrieveListener = new MovieFavouritePresenterImp(
                    movieFavouriteModelContract,
                    dbOperationsListener,
                    null);

            retrieveListener.onRetrieveFailed(ERROR_MESSAGE);

            verify(movieFavouriteListListener, never()).onGetFavouriteMoviesFailure(ERROR_MESSAGE);
            verifyNoMoreInteractions(movieFavouriteListListener);
        }

        @Test
        @DisplayName("Do not get favourite movies if movie listener is null")
        void testOnRetrieve_doNotGetFavouriteMovies() {
            final MovieFavouriteModelContract.RetrieveListener retrieveListener = new MovieFavouritePresenterImp(
                    movieFavouriteModelContract,
                    dbOperationsListener,
                    null);
            final Movie favourite = createFavouriteMovie();
            final List<Movie> movieList = new ArrayList<>();
            movieList.add(favourite);
            final Results<Movie> results = new Results<>(movieList);

            retrieveListener.onRetrievedSuccess(results);

            verify(movieFavouriteListListener, never()).onGetFavouriteMoviesSuccess(results);
            verifyNoMoreInteractions(movieFavouriteListListener);
        }

        @Test
        @DisplayName("Display error message when db is null for getting movie")
        void testOnRetrieve_getFavouriteMovies() {
            final MovieFavouriteModelContract.RetrieveListener retrieveListener = new MovieFavouritePresenterImp(
                    movieFavouriteModelContract,
                    dbOperationsListener,
                    movieFavouriteListListener);
            final Movie favourite = createFavouriteMovie();
            final List<Movie> movieList = new ArrayList<>();
            movieList.add(favourite);
            final Results<Movie> results = new Results<>(movieList);

            retrieveListener.onRetrievedSuccess(results);

            verify(movieFavouriteListListener).onGetFavouriteMoviesSuccess(results);
            verifyNoMoreInteractions(movieFavouriteListListener);
        }

        private MovieFavouriteModelContract.InsertListener createInsertListenerWithNullModel() {
            return new MovieFavouritePresenterImp(
                    movieFavouriteModelContract,
                    null,
                    movieFavouriteListListener);
        }

        private MovieFavouriteModelContract.InsertListener createInsertListenerModel() {
            return new MovieFavouritePresenterImp(
                    movieFavouriteModelContract,
                    dbOperationsListener,
                    movieFavouriteListListener);
        }
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

    private void setupMocks() {
        movieFavouriteModelContract = mock(MovieFavouriteModelContract.class);
        dbOperationsListener = mock(MovieFavouritePresenterContract.DbOperationsListener.class);
        movieFavouriteListListener = mock(MovieFavouriteListListener.class);
    }
*/
}