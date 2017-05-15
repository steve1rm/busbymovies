package me.androidbox.busbymovies.moviedetails;

import javax.inject.Inject;

import me.androidbox.busbymovies.di.DaggerInjector;
import me.androidbox.busbymovies.models.Movie;
import me.androidbox.busbymovies.models.Results;
import me.androidbox.busbymovies.models.Review;
import me.androidbox.busbymovies.models.Trailer;
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

    /* Testing ONLY */
    public MovieDetailModelImp(MovieAPIService movieAPIService) {
        this.mMovieAPIService = movieAPIService;
    }

    @Override
    public void getMovieDetail(int movieId, GetMovieDetailListener getMovieDetailListener) {
        Timber.d("getMovieDetail %d", movieId);

        if(Constants.MOVIES_API_KEY.isEmpty()) {
            getMovieDetailListener.onGetMovieDetailFailure("Empty API Key");
        }
        else {
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
    }

    @Override
    public void getMovieTrailer(int movieId, GetMovieTrailerListener getMovieTrailerListener) {
        if(Constants.MOVIES_API_KEY.isEmpty()) {
            getMovieTrailerListener.onGetMovieTrailerFailure("No API key");
        }
        else {
            mSubscription = mMovieAPIService.getMovieTrailers(movieId, Constants.MOVIES_API_KEY)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<Results<Trailer>>() {
                        @Override
                        public void onCompleted() {
                            Timber.d("onCompleted");
                        }

                        @Override
                        public void onError(Throwable e) {
                            Timber.e(e, "onError %s", e.getMessage());
                            getMovieTrailerListener.onGetMovieTrailerFailure(e.getMessage());
                        }

                        @Override
                        public void onNext(Results<Trailer> trailers) {
                            getMovieTrailerListener.onGetMovieTrailerSuccess(trailers);
                        }
                    });
        }
    }

    @Override
    public void getMovieReviews(int movieId, MovieReviewsListener movieReviewsListener) {
        if(Constants.MOVIES_API_KEY.isEmpty()) {
            movieReviewsListener.onGetMovieReviewsFailure("No API KEY");
        }
        else {
            mSubscription = mMovieAPIService.getMovieReview(movieId, Constants.MOVIES_API_KEY)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<Results<Review>>() {
                        @Override
                        public void onCompleted() {
                            Timber.d("onCompleted");
                        }

                        @Override
                        public void onError(Throwable e) {
                            Timber.e(e, "onError: %s", e.getMessage());
                            movieReviewsListener.onGetMovieReviewsFailure(e.getMessage());
                        }

                        @Override
                        public void onNext(Results<Review> reviewsResults) {
                            Timber.d("onNext");
                            movieReviewsListener.onGetMovieReviewsSuccess(reviewsResults);
                        }
                    });
        }
    }

    @Override
    public void releaseResources() {
        if(mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
            mSubscription = null;
        }
    }
































}
