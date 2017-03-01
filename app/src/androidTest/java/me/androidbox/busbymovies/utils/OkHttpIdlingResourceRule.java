package me.androidbox.busbymovies.utils;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;

import com.jakewharton.espresso.OkHttp3IdlingResource;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import me.androidbox.busbymovies.network.OkHttpProvider;

/**
 * Created by steve on 3/1/17.
 */

public class OkHttpIdlingResourceRule implements TestRule {

    @Override
    public Statement apply(Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                final IdlingResource idlingResource = OkHttp3IdlingResource.create(
                        Constants.OKHTTP, OkHttpProvider.getOkHttpClientInstance());

                Espresso.registerIdlingResources(idlingResource);

                base.evaluate();

                Espresso.unregisterIdlingResources(idlingResource);
            }
        };
    }
}
