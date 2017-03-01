package me.androidbox.busbymovies.utils;

import me.androidbox.busbymovies.di.BusbyMoviesApplication;

/**
 * Created by steve on 3/1/17.
 */

public class TestBusbyMoviesApplication extends BusbyMoviesApplication {
    private String mBaseUrl;

    @Override
    public String getBaseUrl() {
        return mBaseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        mBaseUrl = baseUrl;
    }
}
