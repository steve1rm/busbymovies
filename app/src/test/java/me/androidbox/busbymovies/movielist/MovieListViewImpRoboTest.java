package me.androidbox.busbymovies.movielist;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.ProgressBar;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import me.androidbox.busbymovies.R;
import me.androidbox.busbymovies.adapters.MovieAdapter;
import me.androidbox.busbymovies.models.Movies;
import me.androidbox.busbymovies.models.Results;
import me.androidbox.busbymovies.support.ResourceLocator;
import me.androidbox.busbymovies.support.ViewLocator;
import support.BaseRobolectricTestRunner;

import static me.androidbox.busbymovies.support.Asserts.viewIsVisible;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

/**
 * Created by steve on 3/27/17.
 */

public class MovieListViewImpRoboTest extends BaseRobolectricTestRunner {
    private MovieListViewImp movieListViewImp;

    private MovieListPresenterContract movieListPresenterImp;
    private MovieAdapter movieAdapter;

    @Before
    public void setup() {
        movieListViewImp = new MovieListViewImp();
        initializeFragment(movieListViewImp);

        movieListPresenterImp = mock(MovieListPresenterContract.class);
        movieListViewImp.mMovieListPresenterImp = movieListPresenterImp;
        movieAdapter = mock(MovieAdapter.class);
        movieListViewImp.mMovieAdapter = movieAdapter;
    }

    @Test
    public void shouldNotBeNull() throws Exception {
        assertNotNull(movieListViewImp);
    }

    @Test
    public void shouldDisplayCorrectTitle() throws Exception {
        assertThat(movieListViewImp.getActivity().getTitle(), equalTo(ResourceLocator.getString(R.string.app_name)));
    }

    @Test
    public void shouldDisplaySortFab() throws Exception {
        final FloatingActionButton sort = ViewLocator.getFab(movieListViewImp.getActivity(), R.id.fabSort);
        assertNotNull(sort);
        viewIsVisible(sort);
    }

    @Test
    public void shouldDisplayPopularFab() throws Exception {
        final FloatingActionButton sort = ViewLocator.getFab(movieListViewImp.getActivity(), R.id.fabSort);
        final FloatingActionButton popular = ViewLocator.getFab(movieListViewImp.getActivity(), R.id.fabPopular);
        assertNotNull(sort);
        assertNotNull(popular);

        sort.performClick();

        viewIsVisible(popular);
    }

    @Test
    public void shouldDisplayTopRatedFab() throws Exception {
        final FloatingActionButton sort = ViewLocator.getFab(movieListViewImp.getActivity(), R.id.fabSort);
        final FloatingActionButton topRated = ViewLocator.getFab(movieListViewImp.getActivity(), R.id.fabTopRated);

        assertNotNull(sort);
        assertNotNull(topRated);

        sort.performClick();
        viewIsVisible(topRated);
    }

    @Test
    public void shouldDisplayFavouriteFab_onClickingSortFab() {
        final FloatingActionButton sort = ViewLocator.getFab(movieListViewImp.getActivity(), R.id.fabSort);
        final FloatingActionButton favourite = ViewLocator.getFab(movieListViewImp.getActivity(), R.id.fabSort);

        assertThat(sort, is(notNullValue()));
        assertThat(favourite, is(notNullValue()));

        sort.performClick();
        viewIsVisible(favourite);
    }

    private Results<Movies> createMovieResults() {
        final List<Movies> moviesList = new ArrayList<>();
        final Movies movies = new Movies(
                12345,
                "poster_path",
                "overview",
                "release_date",
                "title",
                "backdrop_path",
                7.4F,
                4.2F);
        moviesList.add(movies);

        return new Results<>(moviesList);
    }

    @Test
    public void testSuccessToGetSearchMovies_HideProgressBar_loadAdapter() {
        final ProgressBar pbMovieList = ViewLocator.getProgressBar(movieListViewImp.getActivity(), R.id.pbMovieList);
        assertThat(pbMovieList, is(notNullValue()));
        final Results<Movies> moviesResults = createMovieResults();

        movieListViewImp.successToGetSearchMovies(moviesResults);

        verify(movieListViewImp.mMovieAdapter).loadAdapter(moviesResults);
     //   Assert.Companion.viewIsGone(pbMovieList);
    }

    @Test
    public void testGetTopRatedMovies_showProgressBar_getTopRatedMovies() {
        movieListViewImp.getTopRated();

        verify(movieListViewImp.mMovieListPresenterImp).getTopRatedMovies();
        verify(movieListViewImp.mMovieListPresenterImp).closeSortFab();
        verifyNoMoreInteractions(movieListViewImp.mMovieListPresenterImp);
    }

    @Test
    public void shouldNotDisplayTitleOnDetailPage() throws Exception {
        final RecyclerView recyclerView = ViewLocator.getRecyclerView(movieListViewImp.getActivity(), R.id.rvMovieList);
        assertNotNull(recyclerView);

  //      recyclerView.performClick();
/*

        ShadowActivity shadowActivity = shadowOf(mMovieListActivity);
        Intent intent = shadowActivity.getNextStartedActivity();
        ShadowIntent shadowIntent = shadowOf(intent);
        assertEquals(shadowIntent.getIntentClass().getSimpleName(), MovieDetailActivity.class.getSimpleName());
*/
    }
}