package me.androidbox.busbymovies.movielist;

import android.app.Instrumentation;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import me.androidbox.busbymovies.di.TestBusbyMoviesMainApplication;

import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by steve on 11/30/17.
 */
public class MovieListViewImpTest {

    private TestBusbyMoviesMainApplication testBusbyMoviesMainApplication;

    @Rule
    public ActivityTestRule<MovieListActivity> movieListActivity = new ActivityTestRule<>(
            MovieListActivity.class,
            true,
            false);

    @Before
    public void setup() {
        final Instrumentation instrumentation =
                InstrumentationRegistry.getInstrumentation();

        testBusbyMoviesMainApplication =
                (TestBusbyMoviesMainApplication)instrumentation
                        .getTargetContext()
                        .getApplicationContext();

        testBusbyMoviesMainApplication.createAppComponent().inject(MovieListViewImpTest.this);
    }

    @Test
    public void testBusbyMoviesMainApplication_isNonNull() {
        assertThat(testBusbyMoviesMainApplication, is(notNullValue()));
    }
}