package me.androidbox.busbymovies.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import me.androidbox.busbymovies.movielist.MovieListModelContract;
import me.androidbox.busbymovies.movielist.MovieListModelImp;
import me.androidbox.busbymovies.movielist.MovieListPresenterContract;
import me.androidbox.busbymovies.movielist.MovieListPresenterImp;
import me.androidbox.busbymovies.movielist.MovieListViewImp;

/**
 * Created by steve on 2/18/17.
 */

@Module
public class PresenterModule {
    @Provides
    @Singleton
    public MovieListModelContract provideMovieListModel() {
        return new MovieListModelImp();
    }

    @Provides
    @Singleton
    public MovieListPresenterContract<MovieListViewImp> providesMovieListPresenter() {
        return new MovieListPresenterImp();
    }
}
