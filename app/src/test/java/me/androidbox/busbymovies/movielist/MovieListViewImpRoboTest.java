package me.androidbox.busbymovies.movielist;

import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import me.androidbox.busbymovies.BuildConfig;
import me.androidbox.busbymovies.R;
import me.androidbox.busbymovies.di.BusbyMoviesMainApplication;
import me.androidbox.busbymovies.support.Asserts;
import me.androidbox.busbymovies.support.ResourceLocator;
import me.androidbox.busbymovies.support.ViewLocator;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

/**
 * Created by steve on 3/27/17.
 */

@Config(packageName = "me.androidbox.busbymovies",
        sdk = Build.VERSION_CODES.LOLLIPOP,
        application = BusbyMoviesMainApplication.class,
        constants = BuildConfig.class)
@RunWith(RobolectricTestRunner.class)
public class MovieListViewImpRoboTest {
    private MovieListActivity mMovieListActivity;

    @Before
    public void setup() {
        mMovieListActivity = Robolectric.setupActivity(MovieListActivity.class);
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
        Asserts.viewIsVisible(sort);
    }

    @Test
    public void shouldDisplayPopularFab() throws Exception {
        FloatingActionButton sort = ViewLocator.getFab(mMovieListActivity, R.id.fabSort);
        FloatingActionButton popular = ViewLocator.getFab(mMovieListActivity, R.id.fabPopular);
        assertNotNull(sort);
        assertNotNull(popular);

        sort.performClick();
        
        Asserts.viewIsVisible(popular);
    }

    @Test
    public void shouldDisplayTopRatedFab() throws Exception {
        FloatingActionButton sort = ViewLocator.getFab(mMovieListActivity, R.id.fabSort);
        FloatingActionButton topRated = ViewLocator.getFab(mMovieListActivity, R.id.fabTopRated);

        assertNotNull(sort);
        assertNotNull(topRated);

        sort.performClick();
        Asserts.viewIsVisible(topRated);
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