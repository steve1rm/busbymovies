package me.androidbox.busbymovies.utils;

import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.ViewAssertion;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import org.hamcrest.Matcher;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by steve on 3/1/17.
 */

public class RecyclerViewAssertions implements ViewAssertion {
    private final Matcher<Integer> mMatcher;

    public RecyclerViewAssertions(Matcher<Integer> matcher) {
        mMatcher = matcher;
    }

    public RecyclerViewAssertions(int expectedCount) {
        mMatcher = is(expectedCount);
    }

    @Override
    public void check(View view, NoMatchingViewException noViewFoundException) {
        if(noViewFoundException != null) {
            throw noViewFoundException;
        }
        else {
            final RecyclerView recyclerView = (RecyclerView)view;
            recyclerView.setAdapter(recyclerView.getAdapter());

            assertThat(recyclerView.getAdapter().getItemCount(), mMatcher);
        }
    }
}
