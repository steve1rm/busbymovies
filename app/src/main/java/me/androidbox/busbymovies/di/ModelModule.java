package me.androidbox.busbymovies.di;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import me.androidbox.busbymovies.data.MovieFavouriteModelContract;
import me.androidbox.busbymovies.data.MovieFavouriteModelImp;
import me.androidbox.busbymovies.moviedetails.MovieDetailModelContract;
import me.androidbox.busbymovies.moviedetails.MovieDetailModelImp;

/**
 * Created by steve on 2/19/17.
 */
@Module
public class ModelModule {
    private Application mApplication;

    public ModelModule(Application application) {
        mApplication = application;
    }

   /* @Provides
    @Singleton
    public MovieListModelContract provideMovieListModel() {
        return new MovieListModelImp();
    }
*/
    @Provides
    @Singleton
    public MovieDetailModelContract provideMovieDetailModel() {
        return new MovieDetailModelImp();
    }

    @Provides
    @Singleton
    public MovieFavouriteModelContract provideMovieFavouriteModel() {
        return new MovieFavouriteModelImp(mApplication);
    }
}
