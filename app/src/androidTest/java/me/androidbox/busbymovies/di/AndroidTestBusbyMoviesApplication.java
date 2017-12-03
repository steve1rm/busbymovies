package me.androidbox.busbymovies.di;

import org.jetbrains.annotations.NotNull;

/**
 * Created by steve on 12/2/17.
 */

public class AndroidTestBusbyMoviesApplication extends BusbyMoviesMainApplication {
    @NotNull
    @Override
    public AndroidTestBusbyMoviesAppComponent createAppComponent() {
        final AndroidTestBusbyMoviesAppComponent androidTestBusbyMoviesAppComponent;

        androidTestBusbyMoviesAppComponent = DaggerAndroidTestBusbyMoviesAppComponent
                .builder()
                .build();

        return androidTestBusbyMoviesAppComponent;
    }
}
