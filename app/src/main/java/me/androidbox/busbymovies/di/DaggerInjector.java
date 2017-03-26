package me.androidbox.busbymovies.di;

/**
 * Created by steve on 2/19/17.
 */

public class DaggerInjector {
    private static BusbyMoviesApplication mApplication;
    private static ApplicationComponent sApplicationComponent;

    public DaggerInjector(BusbyMoviesApplication application) {
        mApplication = application;

        sApplicationComponent = DaggerApplicationComponent.builder()
                .presenterModule(new PresenterModule())
                .presenterDetailModule(new PresenterDetailModule())
                .modelModule(new ModelModule(mApplication))
                .apiModule(new ApiModule(mApplication))
                .applicationModule(new ApplicationModule(mApplication))
                .build();

    }
/*

    private static ApplicationComponent sApplicationComponent = DaggerApplicationComponent.builder()
            .presenterModule(new PresenterModule())
            .presenterDetailModule(new PresenterDetailModule())
            .modelModule(new ModelModule(mApplication))
            .apiModule(new ApiModule(mApplication))
            .applicationModule(new ApplicationModule(mApplication))
            .build();
*/

    public static ApplicationComponent getApplicationComponent() {
        return sApplicationComponent;
    }
}
