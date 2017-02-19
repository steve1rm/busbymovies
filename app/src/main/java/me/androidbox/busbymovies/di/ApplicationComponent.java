package me.androidbox.busbymovies.di;

import javax.inject.Singleton;

import dagger.Component;
import me.androidbox.busbymovies.movielist.MovieListModelImp;
import me.androidbox.busbymovies.movielist.MovieListPresenterImp;
import me.androidbox.busbymovies.movielist.MovieListViewImp;

/**
 * Created by steve on 2/18/17.
 */

@Singleton
@Component(modules = {PresenterModule.class, ModelModule.class, ApplicationModule.class, ApiModule.class})
public interface ApplicationComponent {
    void inject(MovieListViewImp target);
    void inject(BusbyMoviesApplication target);
    void inject(MovieListModelImp target);
    void inject(MovieListPresenterImp target);
}
