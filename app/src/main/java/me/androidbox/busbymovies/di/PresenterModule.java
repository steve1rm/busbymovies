package me.androidbox.busbymovies.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import me.androidbox.busbymovies.movielist.MovieListPresenterContract;
import me.androidbox.busbymovies.movielist.MovieListPresenterImp;
import me.androidbox.busbymovies.movielist.MovieListViewContract;

/**
 * Created by steve on 2/18/17.
 */

@Module
public class PresenterModule {
    @Provides
    @Singleton
    public MovieListPresenterContract<MovieListViewContract> providesMovieListPresenter() {
        return new MovieListPresenterImp();
    }
}
