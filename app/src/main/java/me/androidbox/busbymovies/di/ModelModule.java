package me.androidbox.busbymovies.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import me.androidbox.busbymovies.movielist.MovieListModelContract;
import me.androidbox.busbymovies.movielist.MovieListModelImp;

/**
 * Created by steve on 2/19/17.
 */
@Module
public class ModelModule {
    @Provides
    @Singleton
    public MovieListModelContract provideMovieListModel() {
        return new MovieListModelImp();
    }
}
