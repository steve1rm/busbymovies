package me.androidbox.busbymovies.movielist;

import android.app.Instrumentation;
import android.content.Intent;
import androidx.test.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import di.AndroidTestBusbyMoviesMainApplication;
import me.androidbox.busbymovies.R;
import me.androidbox.busbymovies.models.Movie;
import me.androidbox.busbymovies.models.Movies;
import me.androidbox.busbymovies.models.Results;
import me.androidbox.busbymovies.network.MovieAPIService;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Created by steve on 11/30/17.
 */
@RunWith(MockitoJUnitRunner.class)
public class MovieListViewImpAndroidTest {
    @Inject
    MovieAPIService movieAPIService;

    @Mock
    Results<Movies> mockMovies;

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

        androidTestBusbyMoviesApplication.getMovieListComponent().inject(MovieListViewImpAndroidTest.this);
    }

    @Test
    public void testMovieAPIService_notNullValue() {
        assertThat(movieAPIService, is(notNullValue()));
    }

    @Test
    public void testBusbyMoviesMainApplication_isNonNull() {
        final Results<Movies> moviesResults = new Results<>();
        final List<Movies> moviesList = new ArrayList<>();
        moviesList.add(new Movie(
                1234,
                "poster_path",
                "overview",
                "release_date",
                "title",
                "backdrop_path",
                1145L,
                2344L));
        moviesResults.setResults(moviesList);

        when(movieAPIService.getPopular(anyString())).thenReturn(Observable.just(moviesResults));

        movieListActivity.launchActivity(new Intent());

        onView(withId(R.id.rvMovieList)).check(matches(hasDescendant(withText("title"))));
    }
}