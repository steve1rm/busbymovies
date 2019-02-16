package me.androidbox.busbymovies.busbymovielist;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.IdlingResource;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.jakewharton.espresso.OkHttp3IdlingResource;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import me.androidbox.busbymovies.R;
import me.androidbox.busbymovies.movielist.MovieListActivity;
import me.androidbox.busbymovies.network.OkHttpProvider;
import me.androidbox.busbymovies.utils.RecyclerViewAssertions;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
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
        onView(withText(R.string.app_name)).check(matches(isDisplayed()));
        Espresso.unregisterIdlingResources(idlingResource);
    }

    @Test
    public void shouldDisplayTwentyItemsInRecyclerView() {
        onView(withId(R.id.rvMovieList))
                .check(new RecyclerViewAssertions(equalTo(20)));
    }
}
