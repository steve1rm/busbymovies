package me.androidbox.busbymovies.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import me.androidbox.busbymovies.moviedetails.ImageLoader;
import me.androidbox.busbymovies.moviedetails.ImageLoaderImp;
import me.androidbox.busbymovies.moviedetails.MovieDetailPresenterContract;
import me.androidbox.busbymovies.moviedetails.MovieDetailPresenterImp;
import me.androidbox.busbymovies.moviedetails.MovieDetailViewContract;

/**
 * Created by steve on 3/2/17.
 */

@Module
public class PresenterDetailModule {

    @Provides
    @Singleton
    public MovieDetailPresenterContract<MovieDetailViewContract> provideMovieDetailPresenter() {
        return new MovieDetailPresenterImp();
    }

    @Provides
    @Singleton
    public ImageLoader provideImageLoader() {
        return new ImageLoaderImp();
    }
}
