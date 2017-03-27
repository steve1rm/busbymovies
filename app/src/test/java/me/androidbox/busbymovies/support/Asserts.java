package me.androidbox.busbymovies.support;

import android.view.View;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created by steve on 3/28/17.
 */

public final class Asserts {
    private Asserts() {}

    public static void viewIsVisible(View view) {
        assertThat(view.getVisibility(), equalTo(View.VISIBLE));
    }
}
