package me.androidbox.busbymovies.movielist;

import javax.inject.Inject;

import me.androidbox.busbymovies.di.DaggerInjector;
import me.androidbox.busbymovies.models.Movie;
import me.androidbox.busbymovies.models.Results;
import me.androidbox.busbymovies.network.MovieAPIService;
import me.androidbox.busbymovies.utils.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

/**
 * Created by steve on 2/19/17.
 */

public class MovieListModelImp implements MovieListModelContract {

    @Inject MovieAPIService mMovieAPIService;

    public MovieListModelImp() {
        DaggerInjector.getApplicationComponent().inject(MovieListModelImp.this);
        if(mMovieAPIService == null) {
            Timber.e("mMovieAPIService == null");
        }
    }

    @Override
    public void getPopularMovies(PopularMovieResultsListener popularMovieResultsListener) {
        mMovieAPIService.getPopular(Constants.MOVIES_API_KEY).enqueue(new Callback<Results>() {
            @Override
            public void onResponse(Call<Results> call, Response<Results> response) {
                Timber.d("Response: %s", response.body());
                popularMovieResultsListener.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<Results> call, Throwable t) {
                Timber.e(t, "onFailure");
                popularMovieResultsListener.onFailure(t.getMessage());
            }
        });
    }

    @Override
    public void getMovie(int movieId, PopularMovieResultsListener popularMovieResultsListener) {
        mMovieAPIService.getMovie(movieId, Constants.MOVIES_API_KEY).enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                Timber.d("Response: %s", response.body().getTitle());
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                Timber.e(t, "onFailure");
            }
        });
    }
}
