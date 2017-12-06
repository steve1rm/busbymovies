package me.androidbox.busbymovies.movielist;

import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import javax.inject.Inject;

import me.androidbox.busbymovies.di.AndroidTestBusbyMoviesMainApplication;
import me.androidbox.busbymovies.network.MovieAPIService;

import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by steve on 11/30/17.
 */
public class MovieListViewImpTest {
    @Inject
    MovieAPIService movieAPIService;

    @Rule
    public ActivityTestRule<MovieListActivity> movieListActivity = new ActivityTestRule<>(
            MovieListActivity.class,
            true,
            false);

    @Before
    public void setup() {
        final Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        final AndroidTestBusbyMoviesMainApplication androidTestBusbyMoviesApplication =
                (AndroidTestBusbyMoviesMainApplication)instrumentation
                        .getTargetContext()
                        .getApplicationContext();

        androidTestBusbyMoviesApplication.createAppComponent().inject(MovieListViewImpTest.this);
    }

    @Test
    public void testMovieAPIService_notNullValue() {
        assertThat(movieAPIService, is(notNullValue()));
    }

    @Test
    public void testBusbyMoviesMainApplication_isNonNull() {
        movieListActivity.launchActivity(new Intent());
    }
}