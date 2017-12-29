package me.androidbox.busbymovies.movielist;

import android.support.v4.widget.ContentLoadingProgressBar;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import me.androidbox.busbymovies.adapters.MovieAdapter;
import me.androidbox.busbymovies.data.MovieFavouritePresenterContract;
import me.androidbox.busbymovies.models.Movies;
import me.androidbox.busbymovies.models.Results;
import support.BaseRobolectricTestRunner;

import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * Created by steve on 12/27/17.
 */
@RunWith(MockitoJUnitRunner.class)
public class MovieListViewImpTest extends BaseRobolectricTestRunner {
    private MovieListViewImp movieListViewImp;

    @Mock
    private MovieListPresenterContract movieListPresenter;
    @Mock
    private MovieFavouritePresenterContract movieFavouritePresenter;
    @Mock
    private ContentLoadingProgressBar pbMovieList;
    @Mock
    private MovieAdapter movieAdapter;

    @Before
    public void setup() {
        movieListViewImp = MovieListViewImp.newInstance();
        movieListViewImp.mMovieListPresenterImp = movieListPresenter;
        movieListViewImp.mMovieFavouritePresenterImp = movieFavouritePresenter;
        movieListViewImp.mPbMovieList = pbMovieList;
        movieListViewImp.mMovieAdapter = movieAdapter;
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

    @Test
    public void testOnShowProgressBar_showProgressBar_whenHidden() {
        when(pbMovieList.isShown()).thenReturn(false);

        movieListViewImp.onShowProgressBar();

        verify(pbMovieList).isShown();
        verify(pbMovieList).show();
        verifyNoMoreInteractions(pbMovieList);
    }

    @Test
    public void testOnShowProgressBar_doNothing_whenAlreadyHidden() {
        when(pbMovieList.isShown()).thenReturn(true);

        movieListViewImp.onShowProgressBar();

        verify(pbMovieList).isShown();
        verifyNoMoreInteractions(pbMovieList);
    }

    @Test
    public void testOnHideProgressBar_hideProgressBar_whenShown() {
        when(pbMovieList.isShown()).thenReturn(true);

        movieListViewImp.onHideProgressBar();

        verify(pbMovieList).isShown();
        verify(pbMovieList).hide();
        verifyNoMoreInteractions(pbMovieList);
    }

    @Test
    public void testOnHideProgressBar_doNothing_whenAlreadyShown() {
        when(pbMovieList.isShown()).thenReturn(false);

        movieListViewImp.onHideProgressBar();

        verify(pbMovieList).isShown();
        verifyNoMoreInteractions(pbMovieList);
    }

    @Test
    public void testSuccessToGetSearchMovies_loadAdapter() {
        final Results<Movies> moviesResults = createMovieResults();
        movieListViewImp.successToGetSearchMovies(moviesResults);

        verify(movieAdapter).loadAdapter(moviesResults);
        verifyNoMoreInteractions(movieAdapter);
    }

    @Test(expected = NullPointerException.class)
    public void testOnMovieSearch_throwNPE_whenMovieNameIsNullValue() {
        movieListViewImp.onMovieSearch(null, 0);
    }

    @Ignore
    @Test
    public void testOnMovieSearch_doNothing_withEmptyMovieName() {
      //  initializeFragment(movieListViewImp);

        movieListViewImp.onMovieSearch("Great movie", 1982);

        verify(movieListPresenter, never()).searchMovies("", 1982);
        verifyNoMoreInteractions(movieListPresenter);
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
}