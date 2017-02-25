package me.androidbox.busbymovies.di;

/**
 * Created by steve on 2/19/17.
 */

public class DaggerInjector {
    private static BusbyMoviesApplication mApplication;

    public DaggerInjector(BusbyMoviesApplication application) {
        mApplication = application;
    }

    private static ApplicationComponent sApplicationComponent = DaggerApplicationComponent.builder()
            .presenterModule(new PresenterModule())
            .modelModule(new ModelModule())
            .apiModule(new ApiModule(mApplication))
            .applicationModule(new ApplicationModule(mApplication))
            .build();

    public static ApplicationComponent getApplicationComponent() {
        return sApplicationComponent;
    }
}
