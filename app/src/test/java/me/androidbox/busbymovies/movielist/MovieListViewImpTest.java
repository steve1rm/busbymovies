package me.androidbox.busbymovies.movielist;

import android.app.Dialog;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.robolectric.shadows.ShadowDialog;
import org.robolectric.shadows.ShadowToast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.Unbinder;
import me.androidbox.busbymovies.R;
import me.androidbox.busbymovies.adapters.MovieAdapter;
import me.androidbox.busbymovies.data.MovieFavouritePresenterContract;
import me.androidbox.busbymovies.models.Movie;
import me.androidbox.busbymovies.models.Movies;
import me.androidbox.busbymovies.models.Results;
import me.androidbox.busbymovies.support.ResourceLocator;
import me.androidbox.busbymovies.support.ViewLocator;
import support.BaseRobolectricTestRunner;

import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * Created by steve on 12/27/17.
 */
public class MovieListViewImpTest extends BaseRobolectricTestRunner {
    private MovieListViewImp movieListViewImp;
    private final String ERROR_MESSAGE = "Failure message to get movies";

    @Before
    public void setup() {
        movieListViewImp = MovieListViewImp.newInstance();
        setupMocks();
    }

    @Test
    public void testMovieListViewImp_isNotNullValue() {
        assertThat(movieListViewImp, is(notNullValue()));
    }

    @Test
    public void testGetPopular_getsPopularMovies() {
        movieListViewImp.getPopular();

        verify(movieListViewImp.mMovieListPresenterImp).getPopularMovies();
        verify(movieListViewImp.mMovieListPresenterImp).closeSortFab();
        verifyNoMoreInteractions(movieListViewImp.mMovieListPresenterImp);
    }

    @Test
    public void testGetTopRated_getsTopRated() {
        movieListViewImp.getTopRated();

        verify(movieListViewImp.mMovieListPresenterImp).getTopRatedMovies();
        verify(movieListViewImp.mMovieListPresenterImp).closeSortFab();
        verifyNoMoreInteractions(movieListViewImp.mMovieListPresenterImp);
    }

    @Test
    public void testGetFavourites_getsFavourites() {
        movieListViewImp.getFavourites();

        verify(movieListViewImp.mMovieFavouritePresenterImp).getFavouriteMovies();
        verify(movieListViewImp.mMovieListPresenterImp).closeSortFab();
        verifyNoMoreInteractions(movieListViewImp.mMovieFavouritePresenterImp);
        verifyNoMoreInteractions(movieListViewImp.mMovieListPresenterImp);
    }

    @Test
    public void testOnShowProgressBar_showProgressBar_whenHidden() {
        when(movieListViewImp.mPbMovieList.isShown()).thenReturn(false);

        movieListViewImp.onShowProgressBar();

        verify(movieListViewImp.mPbMovieList).isShown();
        verify(movieListViewImp.mPbMovieList).show();
        verifyNoMoreInteractions(movieListViewImp.mPbMovieList);
    }

    @Test
    public void testOnShowProgressBar_doNothing_whenAlreadyHidden() {
        when(movieListViewImp.mPbMovieList.isShown()).thenReturn(true);

        movieListViewImp.onShowProgressBar();

        verify(movieListViewImp.mPbMovieList).isShown();
        verifyNoMoreInteractions(movieListViewImp.mPbMovieList);
    }

    @Test
    public void testOnHideProgressBar_hideProgressBar_whenShown() {
        when(movieListViewImp.mPbMovieList.isShown()).thenReturn(true);

        movieListViewImp.onHideProgressBar();

        verify(movieListViewImp.mPbMovieList).isShown();
        verify(movieListViewImp.mPbMovieList).hide();
        verifyNoMoreInteractions(movieListViewImp.mPbMovieList);
    }

    @Test
    public void testOnHideProgressBar_doNothing_whenAlreadyShown() {
        when(movieListViewImp.mPbMovieList.isShown()).thenReturn(false);

        movieListViewImp.onHideProgressBar();

        verify(movieListViewImp.mPbMovieList).isShown();
        verifyNoMoreInteractions(movieListViewImp.mPbMovieList);
    }

    @Test
    public void testSuccessToGetSearchMovies_loadAdapter() {
        final Results<Movies> moviesResults = createMovieResults();
        movieListViewImp.successToGetSearchMovies(moviesResults);

        verify(movieListViewImp.mMovieAdapter).loadAdapter(moviesResults);
        verifyNoMoreInteractions(movieListViewImp.mMovieAdapter);
    }

    @Test(expected = NullPointerException.class)
    public void testOnMovieSearch_throwNPE_whenMovieNameIsNullValue() {
        movieListViewImp.onMovieSearch(null, 0);
    }

    @Test
    public void testOnMovieSearch_doMovieSearch_whenMovieNameIsNotEmpty() {
        movieListViewImp.onMovieSearch("Great movie", 1982);

        verify(movieListViewImp.mMovieListPresenterImp).searchMovies("Great movie", 1982);
        verifyNoMoreInteractions(movieListViewImp.mMovieListPresenterImp);
    }

    @Test
    public void testOnMovieSearch_doNothing_whenMovieNameIsEmpty() {
        movieListViewImp.onMovieSearch("", 2002);

        verify(movieListViewImp.mMovieListPresenterImp, never()).searchMovies("", 2002);
        verifyNoMoreInteractions(movieListViewImp.mMovieListPresenterImp);
    }

    @Test
    public void testGetFavouriteMovieSuccess_DoNotLoadAdapter_whenListEmpty() {
        final Results<Movie> movieResults = createEmptyFavouriteList();

        movieListViewImp.onGetFavouriteMoviesSuccess(movieResults);

        verify(movieListViewImp.mMovieAdapter, never()).loadAdapter(movieResults);
        verifyNoMoreInteractions(movieListViewImp.mMovieAdapter);
    }

    @Test
    public void testGetFavouriteMovieSuccess_LoadAdapter_whenListNotEmpty() {
        final Results<Movie> movieResults = createFavouriteList();

        movieListViewImp.onGetFavouriteMoviesSuccess(movieResults);

        verify(movieListViewImp.mMovieAdapter).loadAdapter(movieResults);
        verifyNoMoreInteractions(movieListViewImp.mMovieAdapter);
    }

    @Test
    public void testOnDestroyView_closeSortFab_andDetachView() {
        movieListViewImp.mUnbinder = mock(Unbinder.class);

        movieListViewImp.onDestroyView();

        verify(movieListViewImp.mMovieListPresenterImp).closeSortFab();
        verify(movieListViewImp.mMovieListPresenterImp).detachView();
        verifyNoMoreInteractions(movieListViewImp.mMovieListPresenterImp);
        verify(movieListViewImp.mUnbinder).unbind();
        verifyNoMoreInteractions(movieListViewImp.mUnbinder);
    }

    @Test
    public void onCloseSortFab_ifSortOpen_setToFalse() {
        initializeFragment(movieListViewImp);
        movieListViewImp.mIsSortFabOpen = true;

        movieListViewImp.onCloseSortFab();

        assertThat(movieListViewImp.mIsSortFabOpen, is(false));
    }

    @Test
    public void onCloseSortFab_ifSortOpen_setToTrue() {
        initializeFragment(movieListViewImp);
        movieListViewImp.mIsSortFabOpen = true;

        movieListViewImp.onCloseSortFab();

        assertThat(movieListViewImp.mIsSortFabOpen, is(false));
    }

    @Test
    public void onOpenSortFab_ifSortOpen_setToFalse() {
        initializeFragment(movieListViewImp);
        movieListViewImp.mIsSortFabOpen = true;

        movieListViewImp.onOpenSortFab();

        assertThat(movieListViewImp.mIsSortFabOpen, is(true));
    }

    @Test
    public void onOpenSortFab_ifSortOpen_setToTrue() {
        initializeFragment(movieListViewImp);
        movieListViewImp.mIsSortFabOpen = false;

        movieListViewImp.onOpenSortFab();

        assertThat(movieListViewImp.mIsSortFabOpen, is(true));
    }

    @Test
    public void testOpenSort_opensSortFab() {
        movieListViewImp.openSort();

        verify(movieListViewImp.mMovieListPresenterImp).openSortFab();
        verifyNoMoreInteractions(movieListViewImp.mMovieListPresenterImp);
    }

    @Test
    public void testDisplayPopularMovies_loadAdapter() {
        final Results<Movies> moviesResults = createMovieResults();

        initializeFragment(movieListViewImp);
        setupMocks();

        movieListViewImp.displayPopularMovies(moviesResults);

        verify(movieListViewImp.mMovieAdapter).loadAdapter(moviesResults);
    }

    @Test
    public void testDisplayTopRatedMovies_loadAdapter() {
        final Results<Movies> moviesResults = createMovieResults();

        movieListViewImp.displayTopRatedMovies(moviesResults);

        verify(movieListViewImp.mMovieAdapter).loadAdapter(moviesResults);
        verifyNoMoreInteractions(movieListViewImp.mMovieAdapter);
    }

    @Test
    public void testFailedToGetSearchMovies_showErrorMessageInToast() {
        movieListViewImp.failedToGetSearchMovies(ERROR_MESSAGE);

        assertThat(ShadowToast.getTextOfLatestToast(), is(ERROR_MESSAGE));
    }

    @Test
    public void testFailedToDisplayTopRatedMovies_showErrorMessageInToast() {
        movieListViewImp.failedToDisplayTopRatedMovies(ERROR_MESSAGE);

        assertThat(ShadowToast.getTextOfLatestToast(), is(ERROR_MESSAGE));
        verify(movieListViewImp.mPbMovieList).hide();
        verifyNoMoreInteractions(movieListViewImp.mPbMovieList);
    }

    @Test
    public void testFailedToDisplayPopularMovies_showErrorMessageInToast() {
        initializeFragment(movieListViewImp);

        movieListViewImp.failedToDisplayPopularMovies(ERROR_MESSAGE);

        assertThat(ShadowToast.getTextOfLatestToast(), is(ERROR_MESSAGE));
    }

    @Test
    public void testOnGetFavouriteMoviesFailure_showErrorMessageInToast() {
        movieListViewImp.onGetFavouriteMoviesFailure(ERROR_MESSAGE);

        assertThat(ShadowToast.getTextOfLatestToast(), is(ERROR_MESSAGE));
    }

    @Test
    public void testSearchMovie_displayDialogFragment() {
        initializeFragment(movieListViewImp);
        setupMocks();

        movieListViewImp.searchMovie();

        verify(movieListViewImp.mMovieListPresenterImp).closeSortFab();
        verifyNoMoreInteractions(movieListViewImp.mMovieListPresenterImp);
    }

    @Test
    public void testSearchMovie_dialogDisplayed() {
        initializeFragment(movieListViewImp);
        final FloatingActionButton searchFab = ViewLocator.getFab(movieListViewImp.getActivity(), R.id.fabSearch);

        searchFab.performClick();

        final Dialog dialogFragment = ShadowDialog.getLatestDialog();

        assertThat(dialogFragment, is(notNullValue()));
        assertThat(dialogFragment.isShowing(), is(true));
        final TextView title = dialogFragment.findViewById(R.id.tvTitle);
        assertThat(title.getText().toString(), is(ResourceLocator.getString(R.string.movie_search)));
        dialogFragment.dismiss();
        assertThat(dialogFragment.isShowing(), is(false));
    }

    @Test
    public void testOnDestroy_detachView() {
        initializeFragment(movieListViewImp);
        /* Re-assign mocks as when the fragment started real mocks were assigned */
        setupMocks();

        movieListViewImp.onDestroy();

        verify(movieListViewImp.mMovieListPresenterImp).detachView();
        verifyNoMoreInteractions(movieListViewImp.mMovieListPresenterImp);
    }

    private Results<Movie> createFavouriteList() {
        final List<Movie> movies = new ArrayList<>();

        movies.add(new Movie(
                1234,
                "poster_path",
                "overview",
                "release_date",
                "title",
                "backdrop_path",
                4.5F,
                7.8F));

        return new Results<>(movies);
    }

    private Results<Movie> createEmptyFavouriteList() {
        final List<Movie> movies = Collections.emptyList();

        return new Results<>(movies);
    }

    private Results<Movies> createMovieResults() {
        final List<Movies> movies = new ArrayList<>();

        movies.add(new Movies(
                1234,
                "poster_path",
                "overview",
                "release_date",
                "title",
                "backdrop_path",
                4.5F,
                7.8F));

        return new Results<>(movies);
    }

    private void setupMocks() {
        movieListViewImp.mMovieListPresenterImp = mock(MovieListPresenterContract.class);
        movieListViewImp.mMovieFavouritePresenterImp = mock(MovieFavouritePresenterContract.class);
        movieListViewImp.mPbMovieList = mock(ContentLoadingProgressBar.class);
        movieListViewImp.mMovieAdapter = mock(MovieAdapter.class);
    }
}