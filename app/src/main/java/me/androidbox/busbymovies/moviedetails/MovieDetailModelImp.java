package me.androidbox.busbymovies.moviedetails;

import javax.inject.Inject;

import me.androidbox.busbymovies.di.DaggerInjector;
import me.androidbox.busbymovies.models.Movie;
import me.androidbox.busbymovies.network.MovieAPIService;
import me.androidbox.busbymovies.utils.Constants;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by steve on 3/2/17.
 */

public class MovieDetailModelImp implements MovieDetailModelContract {

    private Subscription mSubscription;

    @Inject MovieAPIService mMovieAPIService;

    public MovieDetailModelImp() {
        DaggerInjector.getApplicationComponent().inject(MovieDetailModelImp.this);

        if(mMovieAPIService == null) {
            Timber.e("mMovieAPIService == null");
        }
    }

    @Override
    public void getMovieDetail(int movieId, GetMovieDetailListener getMovieDetailListener) {
        mSubscription = mMovieAPIService.getMovieByIdExt(movieId, Constants.MOVIES_API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Movie>() {
                    @Override
                    public void onCompleted() {
                        Timber.d("onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e, e.getMessage());
                        getMovieDetailListener.onGetMovieDetailFailure(e.getMessage());
                    }

                    @Override
                    public void onNext(Movie movie) {
                        Timber.d("onNext");
                        getMovieDetailListener.onGetMovieDetailSuccess(movie);
                    }
                });
    }

    @Override
    public void releaseResources() {
        if(mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
            mSubscription = null;
        }
    }
}
