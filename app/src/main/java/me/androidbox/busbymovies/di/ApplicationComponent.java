package me.androidbox.busbymovies.di;

import javax.inject.Singleton;

import dagger.Component;
import me.androidbox.busbymovies.data.MovieFavouritePresenterImp;
import me.androidbox.busbymovies.moviedetails.MovieDetailModelImp;
import me.androidbox.busbymovies.moviedetails.MovieDetailPresenterImp;
import me.androidbox.busbymovies.moviedetails.MovieDetailViewImp;
import me.androidbox.busbymovies.movielist.MovieListModelImp;
import me.androidbox.busbymovies.movielist.MovieListPresenterImp;
import me.androidbox.busbymovies.movielist.MovieListViewImp;

/**
 * Created by steve on 2/18/17.
 */

@Singleton
@Component(modules = {
        PresenterModule.class,
        ModelModule.class,
        ApiModule.class,
        PresenterDetailModule.class})

public interface ApplicationComponent {
    void inject(MovieListViewImp target);
    void inject(BusbyMoviesApplication target);
    void inject(MovieListModelImp target);
    void inject(MovieListPresenterImp target);
    void inject(MovieDetailModelImp target);
    void inject(MovieDetailViewImp target);
    void inject(MovieDetailPresenterImp target);
    void inject(MovieFavouritePresenterImp target);
}
