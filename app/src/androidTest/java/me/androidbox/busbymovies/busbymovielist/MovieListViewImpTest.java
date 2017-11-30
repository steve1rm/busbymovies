package me.androidbox.busbymovies.busbymovielist;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.jakewharton.espresso.OkHttp3IdlingResource;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import me.androidbox.busbymovies.R;
import me.androidbox.busbymovies.movielist.MovieListActivity;
import me.androidbox.busbymovies.network.OkHttpProvider;
import me.androidbox.busbymovies.utils.RecyclerViewAssertions;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.equalTo;

/**
 * Created by steve on 2/18/17.
 */
@RunWith(AndroidJUnit4.class)
public class MovieListViewImpTest {

    @Rule
    public final ActivityTestRule<MovieListActivity> activityTestRule =
            new ActivityTestRule<>(MovieListActivity.class);

    @Test
    public void shouldDisplayCorrectTitleInToolbar() {
        IdlingResource idlingResource = OkHttp3IdlingResource.create("okhttp", OkHttpProvider.getOkHttpClientInstance());

        Espresso.registerIdlingResources(idlingResource);
        onView(ViewMatchers.withText(R.string.app_name)).check(ViewAssertions.matches(isDisplayed()));
        Espresso.unregisterIdlingResources(idlingResource);
    }

    @Test
    public void shouldDisplayTwentyItemsInRecyclerView() {
        onView(withId(R.id.rvMovieList))
                .check(new RecyclerViewAssertions(equalTo(20)));
    }
}
