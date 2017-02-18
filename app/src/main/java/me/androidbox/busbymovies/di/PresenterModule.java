package me.androidbox.busbymovies.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import me.androidbox.busbymovies.movielist.MovieListPresenterImp;

/**
 * Created by steve on 2/18/17.
 */

@Module
public class PresenterModule {
    @Provides @Singleton
    public MovieListPresenterImp providesMovieListPresenter() {
        return new MovieListPresenterImp();
    }
}
