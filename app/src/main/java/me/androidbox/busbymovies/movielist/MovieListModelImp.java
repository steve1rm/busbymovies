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
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by steve on 2/19/17.
 */

public class MovieListModelImp implements MovieListModelContract {

    @Inject MovieAPIService mMovieAPIService;
    private Subscription mSubscription;

    public MovieListModelImp() {
        DaggerInjector.getApplicationComponent().inject(MovieListModelImp.this);
        if(mMovieAPIService == null) {
            Timber.e("mMovieAPIService == null");
        }
    }

    public MovieListModelImp(MovieAPIService mMovieAPIService) {
        this.mMovieAPIService = mMovieAPIService;
    }

    @Override
    public void getPopularMovies(PopularMovieResultsListener popularMovieResultsListener) {
        mSubscription = mMovieAPIService.getPopular(Constants.MOVIES_API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Results>() {
            @Override
            public void onCompleted() {
                Timber.d("onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Timber.e(e, "onError");
                popularMovieResultsListener.onFailure(e.getMessage());
            }

            @Override
            public void onNext(Results results) {
                Timber.d("onNext %d", results.getResults().size());
                popularMovieResultsListener.onSuccess(results);
            }
        });
    }

    @Override
    public void getMovie(int movieId, MovieResultsListener movieResultsListener) {
        mMovieAPIService.getMovieById(movieId, Constants.MOVIES_API_KEY).enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                if(response.isSuccessful()) {
                    Timber.d("Response: %s", response.body().getTitle());
                    movieResultsListener.onSuccess(response.body());
                }
                else {
                    Timber.e("onResponse failed to get movie %s", response.message());
                }
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                Timber.e(t, "onFailure");
            }
        });
    }

    @Override
    public void releaseResources() {
        Timber.d("releaseResources");
        if(mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
            Timber.d("subscription unsubscribed");
        }
    }

    @Override
    public Observable<Results> getPopularMovies() {
        return null;
    }

/*
    @Override
    public Observable<Results> getPopularMovies() {

        mSubscription = mMovieAPIService.getPopular(Constants.MOVIES_API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Results>() {
                    @Override
                    public void onCompleted() {
                        Timber.d("onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e, "onError");

                    }

                    @Override
                    public void onNext(Results results) {
                        Timber.d("onNext %d", results.getResults().size());
                        return
                    }
                });
    }*/
}
