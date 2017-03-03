package me.androidbox.busbymovies.di;

import android.app.Application;

import com.wonderkiln.blurkit.BlurKit;

import me.androidbox.busbymovies.utils.Constants;
import timber.log.Timber;

/**
 * Created by steve on 2/18/17.
 */

public class BusbyMoviesApplication extends Application {
    private static BusbyMoviesApplication mBusbyMoviesApplication;

    @Override
    public void onCreate() {
        super.onCreate();

        Timber.plant(new Timber.DebugTree());
//        BlurKit.init(BusbyMoviesApplication.this);

        mBusbyMoviesApplication = BusbyMoviesApplication.this;

        /* Setup dependency injection */
        setupDependencyInjection();
    }

    /* Initialize dependency mapping */
    private void setupDependencyInjection() {
        new DaggerInjector(mBusbyMoviesApplication);
    }

    public static BusbyMoviesApplication getInstance() {
        return mBusbyMoviesApplication;
    }

    public String getBaseUrl() {
        return Constants.BASE_URL;
    }
}