package me.androidbox.busbymovies.movielist;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.widget.ProgressBar;

import org.junit.Before;
import org.junit.Test;
import org.robolectric.Robolectric;
import org.robolectric.util.FragmentTestUtil;

import java.util.ArrayList;
import java.util.List;

import me.androidbox.busbymovies.R;
import me.androidbox.busbymovies.models.Movies;
import me.androidbox.busbymovies.models.Results;
import me.androidbox.busbymovies.support.Asserts;
import support.Assert;
import support.BaseRobolectricTestRunner;
import me.androidbox.busbymovies.support.ResourceLocator;
import me.androidbox.busbymovies.support.ViewLocator;

import static me.androidbox.busbymovies.support.Asserts.viewIsNotVisible;
import static me.androidbox.busbymovies.support.Asserts.viewIsVisible;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

/**
 * Created by steve on 3/27/17.
 */
public class MovieListViewImpRoboTest extends BaseRobolectricTestRunner {
    private MovieListActivity mMovieListActivity;
    private MovieListViewImp movieListViewImp;

    @Before
    public void setup() {
        mMovieListActivity = Robolectric.setupActivity(MovieListActivity.class);

        movieListViewImp = new MovieListViewImp();

        initializeFragment(movieListViewImp);
    }

    @Test
    public void shouldNotBeNull() throws Exception {
        assertNotNull(mMovieListActivity);
    }

    @Test
    public void shouldDisplayCorrectTitle() throws Exception {
        assertThat(mMovieListActivity.getTitle(), equalTo(ResourceLocator.getString(R.string.app_name)));
    }

    @Test
    public void shouldDisplaySortFab() throws Exception {
        FloatingActionButton sort = ViewLocator.getFab(mMovieListActivity, R.id.fabSort);
        assertNotNull(sort);
        viewIsVisible(sort);
    }

    @Test
    public void shouldDisplayPopularFab() throws Exception {
        FloatingActionButton sort = ViewLocator.getFab(mMovieListActivity, R.id.fabSort);
        FloatingActionButton popular = ViewLocator.getFab(mMovieListActivity, R.id.fabPopular);
        assertNotNull(sort);
        assertNotNull(popular);

        sort.performClick();

        viewIsVisible(popular);
    }

    @Test
    public void shouldDisplayTopRatedFab() throws Exception {
        FloatingActionButton sort = ViewLocator.getFab(mMovieListActivity, R.id.fabSort);
        FloatingActionButton topRated = ViewLocator.getFab(mMovieListActivity, R.id.fabTopRated);

        assertNotNull(sort);
        assertNotNull(topRated);

        sort.performClick();
        viewIsVisible(topRated);
    }

    @Test
    public void shouldDisplayFavouriteFab_onClickingSortFab() {
        final FloatingActionButton sort = ViewLocator.getFab(mMovieListActivity, R.id.fabSort);
        final FloatingActionButton favourite = ViewLocator.getFab(mMovieListActivity, R.id.fabSort);

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

        movieListViewImp.successToGetSearchMovies(createMovieResults());

        Assert.Companion.viewIsGone(pbMovieList);

//        viewIsVisible(pbMovieList);


    }

    @Test
    public void shouldNotDisplayTitleOnDetailPage() throws Exception {
        RecyclerView recyclerView = ViewLocator.getRecyclerView(mMovieListActivity, R.id.rvMovieList);
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