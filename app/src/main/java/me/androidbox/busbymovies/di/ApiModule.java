package me.androidbox.busbymovies.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import me.androidbox.busbymovies.network.MovieAPIService;
import me.androidbox.busbymovies.utils.Constants;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by steve on 2/18/17.
 */
@Module
public class ApiModule {

    public Retrofit provideRetrofit(String url) {
        return new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    public MovieAPIService provideMovieService() {
        return provideRetrofit(Constants.BASE_URL).create(MovieAPIService.class);
    }
}
