package me.androidbox.busbymovies;

import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import me.androidbox.busbymovies.movielist.MovieListActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

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
        onView(withText(R.string.app_name)).check(ViewAssertions.matches(isDisplayed()));
    }
}
