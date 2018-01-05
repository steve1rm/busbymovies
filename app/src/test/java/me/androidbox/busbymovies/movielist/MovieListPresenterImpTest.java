package me.androidbox.busbymovies.movielist;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import me.androidbox.busbymovies.models.Movies;
import me.androidbox.busbymovies.models.Results;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

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

    @Test
    public void shouldReleaseModelResourcesWhenDetached() {
        doNothing().when(mockMovieListModelContract).releaseResources();

        movieListPresenterContract.detachView();

        verify(mockMovieListModelContract, times(1)).releaseResources();
        verifyNoMoreInteractions(mockMovieListModelContract);
    }

    @Test
    public void testSearchMoviesOnSuccess() {
        final String MOVIE_NAME = "movie name";
        final int MOVIE_YEAR = 2002;

        movieListPresenterContract.searchMovies(MOVIE_NAME, MOVIE_YEAR);

        verify(mockMovieListModelContract).searchForMovies(eq(MOVIE_NAME), eq(MOVIE_YEAR), any());
        verifyNoMoreInteractions(mockMovieListModelContract);
    }

    @Test
    public void testSearchMovieOnSuccess() {
        final Results<Movies> movies = new Results<>();

        movieListPresenterContract.onSearchSuccess(movies);

        verify(mockMovieListViewContract).successToGetSearchMovies(movies);
        verifyNoMoreInteractions(mockMovieListModelContract);
    }

    @Test
    public void testSearchMovieOnFailureWhenFailedToGetMovies() {
        final String ERROR_MESSAGE = "error_message";

        movieListPresenterContract.onSearchFailure(ERROR_MESSAGE);

        verify(mockMovieListViewContract).failedToGetSearchMovies(ERROR_MESSAGE);
        verifyNoMoreInteractions(mockMovieListModelContract);
    }

    @Test
    public void testOpenSortFab_opensSortFab_whenAttached() {
        movieListPresenterContract.openSortFab();

        verify(movieListPresenterContract.getView()).onOpenSortFab();
        verifyNoMoreInteractions(movieListPresenterContract.getView());
    }

    @Test
    public void testOpenSortFab_doNotOpenSortFab_whenDetached() {
        movieListPresenterContract.detachView();

        movieListPresenterContract.openSortFab();

        assertThat(movieListPresenterContract.getView(), is(nullValue()));
    }

    @Test
    public void testCloseSortFab_closeSortFab_whenAttached() {
        movieListPresenterContract.closeSortFab();

        verify(movieListPresenterContract.getView()).onCloseSortFab();
        verifyNoMoreInteractions(movieListPresenterContract.getView());
    }

    @Test
    public void testCloseSortFab_doNotCloseSortFab_whenDetached() {
        movieListPresenterContract.detachView();

        movieListPresenterContract.closeSortFab();

        assertThat(movieListPresenterContract.getView(), is(nullValue()));
    }

    @Test
    public void testGetPopularMovies_doNothing_whenMovieModelContract_isNullValue() {
        final MovieListPresenterContract movieListPresenterContract =
                new MovieListPresenterImp(null);

        movieListPresenterContract.attachView(mockMovieListViewContract);
        movieListPresenterContract.getPopularMovies();

        verify(movieListPresenterContract.getView(), never()).onShowProgressBar();
        verifyNoMoreInteractions(movieListPresenterContract.getView());
    }

    @Test
    public void testGetPopularMovies_doNothing_whenViewIsNotDetached() {
        MovieListModelContract.PopularMovieResultsListener listener
                = mock(MovieListModelContract.PopularMovieResultsListener.class);
        movieListPresenterContract.detachView();

        movieListPresenterContract.getPopularMovies();

        assertThat(movieListPresenterContract.getView(), is(nullValue()));
        verify(mockMovieListModelContract).releaseResources();
        verify(mockMovieListModelContract, never()).getPopularMovies(listener);
        verifyNoMoreInteractions(mockMovieListModelContract);
    }

    @Test
    public void testGetPopularMovies_getPopularMovies() {
        movieListPresenterContract.getPopularMovies();

        assertThat(movieListPresenterContract.getView(), is(notNullValue()));
        verify(movieListPresenterContract.getView()).onShowProgressBar();
        verifyNoMoreInteractions(movieListPresenterContract.getView());
        verify(mockMovieListModelContract).getPopularMovies(movieListPresenterContract);
        verifyNoMoreInteractions(mockMovieListModelContract);
    }


}