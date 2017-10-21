package me.androidbox.busbymovies.di;

/**
 * Created by steve on 2/19/17.
 */

public class DaggerInjector {
    private static ApplicationComponent sApplicationComponent;

    /* Build dependency injection */
    public DaggerInjector(BusbyMoviesApplication application) {
        sApplicationComponent = DaggerApplicationComponent.builder()
                .presenterModule(new PresenterModule())
                .presenterDetailModule(new PresenterDetailModule())
                .modelModule(new ModelModule(application))
                .apiModule(new ApiModule())
                .build();
    }

    public static ApplicationComponent getApplicationComponent() {
        return sApplicationComponent;
    }

}
