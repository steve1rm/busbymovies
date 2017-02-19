package me.androidbox.busbymovies.movielist;

import java.util.List;

import javax.inject.Inject;

import me.androidbox.busbymovies.di.DaggerInjector;
import me.androidbox.busbymovies.models.Popular;
import me.androidbox.busbymovies.network.MovieAPIService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

/**
 * Created by steve on 2/19/17.
 */

public class MovieListModelImp implements MovieListModelContract {

 //   @Inject MovieAPIService mMovieAPIService;

    public MovieListModelImp() {
/*
        DaggerInjector.getApplicationComponent().inject(MovieListModelImp.this);
        if(mMovieAPIService == null) {
            Timber.e("mMovieAPIService == null");
        }
*/
    }

    @Override
    public void getPopularMovies(MovieResultsListener resultsListener) {
/*
        mMovieAPIService.getPopular().enqueue(new Callback<List<Popular>>() {
            @Override
            public void onResponse(Call<List<Popular>> call, Response<List<Popular>> response) {
                Timber.d("Response: %s", response.body());
            }

            @Override
            public void onFailure(Call<List<Popular>> call, Throwable t) {
                Timber.d(t, "onFailure");
            }
        });
*/
    }

}
