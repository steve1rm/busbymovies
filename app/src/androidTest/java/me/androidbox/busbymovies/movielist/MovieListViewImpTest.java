package me.androidbox.busbymovies.movielist;

import android.app.Instrumentation;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import me.androidbox.busbymovies.di.AndroidTestBusbyMoviesMainApplication;

import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by steve on 11/30/17.
 */
public class MovieListViewImpTest {

    @Rule
    public ActivityTestRule<MovieListActivity> movieListActivity = new ActivityTestRule<>(
            MovieListActivity.class,
            true,
            false);

    @Before
    public void setup() {
        final Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();

        final Context context = instrumentation.getTargetContext().getApplicationContext();

        final AndroidTestBusbyMoviesMainApplication androidTestBusbyMoviesApplication =
                (AndroidTestBusbyMoviesMainApplication)context;

        androidTestBusbyMoviesApplication.createAppComponent().inject(MovieListViewImpTest.this);
    }

    @Test
    public void testBusbyMoviesMainApplication_isNonNull() {

    }
}