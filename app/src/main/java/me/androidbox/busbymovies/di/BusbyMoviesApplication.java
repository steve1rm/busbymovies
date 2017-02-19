package me.androidbox.busbymovies.di;

import android.app.Application;

import timber.log.Timber;

/**
 * Created by steve on 2/18/17.
 */

public class BusbyMoviesApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Timber.plant(new Timber.DebugTree());

        /* Setup dependency injection */
        setupDependencyInjection();
    }

    /* Initialize dependency mapping */
    private void setupDependencyInjection() {
        new DaggerInjector(BusbyMoviesApplication.this);
    }
}