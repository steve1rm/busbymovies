package me.androidbox.busbymovies;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.jakewharton.espresso.OkHttp3IdlingResource;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import me.androidbox.busbymovies.movielist.MovieListActivity;
import me.androidbox.busbymovies.network.OkHttpProvider;
import me.androidbox.busbymovies.utils.Constants;
import me.androidbox.busbymovies.utils.OkHttpIdlingResourceRule;
import me.androidbox.busbymovies.utils.RecyclerViewAssertions;
import me.androidbox.busbymovies.utils.TestBusbyMoviesApplication;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import timber.log.Timber;

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
            new ActivityTestRule<>(MovieListActivity.class, true, false);

/*
    @Rule
    public OkHttpIdlingResourceRule okHttpIdlingResourceRule =
            new OkHttpIdlingResourceRule();
*/

    @Test
    public void shouldDisplayCorrectTitleInToolbar() {
        final MockWebServer mockWebServer = new MockWebServer();

        try {
            mockWebServer.start();

            TestBusbyMoviesApplication testBusbyMoviesApplication =
                    (TestBusbyMoviesApplication) InstrumentationRegistry.getTargetContext().getApplicationContext();
            testBusbyMoviesApplication.setBaseUrl(mockWebServer.url("/").toString());

            mockWebServer.enqueue(new MockResponse().setBody("{\"page\":1,\"results\":[{\"poster_path\":\"\\/7SMCz5724COOYDhY0mj0NfcJqxH.jpg\",\"adult\":false,\"overview\":\"When a wounded Christian Grey tries to entice a cautious Ana Steele back into his life, she demands a new arrangement before she will give him another chance. As the two begin to build trust and find stability, shadowy figures from Christian’s past start to circle the couple, determined to destroy their hopes for a future together.\",\"release_date\":\"2017-02-08\",\"genre_ids\":[18,10749],\"id\":341174,\"original_title\":\"Fifty Shades Darker\",\"original_language\":\"en\",\"title\":\"Fifty Shades Darker\",\"backdrop_path\":\"\\/rXBB8F6XpHAwci2dihBCcixIHrK.jpg\",\"popularity\":196.586297,\"vote_count\":763,\"video\":false,\"vote_average\":6.2}],\"total_results\":19695,\"total_pages\":985}"));

            activityTestRule.launchActivity(null);

            IdlingResource idlingResource = OkHttp3IdlingResource.create(Constants.OKHTTP, OkHttpProvider.getOkHttpClientInstance());

            Espresso.registerIdlingResources(idlingResource);
            onView(withText(R.string.app_name)).check(ViewAssertions.matches(isDisplayed()));
            Espresso.unregisterIdlingResources(idlingResource);

            mockWebServer.shutdown();
        }
        catch(IOException e) {
            Timber.e(e.getMessage());
            try {
                mockWebServer.shutdown();
            } catch (IOException e1) {
                Timber.e(e1.getMessage());
            }
        }
    }

    @Test
    public void shouldDisplayTwentyItemsInRecyclerView() {
        onView(withId(R.id.rvMovieList))
                .check(new RecyclerViewAssertions(equalTo(20)));
    }
}
