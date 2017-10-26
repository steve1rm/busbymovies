package me.androidbox.busbymovies.di;

import android.app.Application;

import me.androidbox.busbymovies.utils.Constants;
import timber.log.Timber;

/**
 * Created by steve on 2/18/17.
 */

public class BusbyMoviesApplication extends Application {
    private static BusbyMoviesApplication mBusbyMoviesApplication;

    public static BusbyMoviesApplication getInstance() {
        return mBusbyMoviesApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Timber.plant(new Timber.DebugTree());

        mBusbyMoviesApplication = BusbyMoviesApplication.this;

        /* Setup dependency injection */
        setupDependencyInjection();
    }

    /* Initialize dependency mapping */
    private void setupDependencyInjection() {
        new DaggerInjector(mBusbyMoviesApplication);
    }


    public String getBaseUrl() {
        return Constants.BASE_URL;
    }
}