package me.androidbox.busbymovies.moviedetails;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import me.androidbox.busbymovies.di.DaggerInjector;
import me.androidbox.busbymovies.models.Actor;
import me.androidbox.busbymovies.models.Cast;
import me.androidbox.busbymovies.models.Movie;
import me.androidbox.busbymovies.models.Movies;
import me.androidbox.busbymovies.models.Results;
import me.androidbox.busbymovies.models.Review;
import me.androidbox.busbymovies.models.Trailer;
import me.androidbox.busbymovies.network.MovieAPIService;
import me.androidbox.busbymovies.utils.Constants;
import me.androidbox.busbymovies.utils.MovieSchedulers;
import timber.log.Timber;

/**
 * Created by steve on 3/2/17.
 */

public class MovieDetailModelImp implements MovieDetailModelContract {

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Inject MovieAPIService mMovieAPIService;
    private MovieSchedulers movieSchedulers;

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
            compositeDisposable.add(mMovieAPIService.getMovieByIdExt(movieId, Constants.MOVIES_API_KEY)
                    .subscribeOn(movieSchedulers.getBackgroundScheduler())
                    .observeOn(movieSchedulers.getUIScheduler())
                    .subscribeWith(new DisposableObserver<Movie>() {
                        @Override
                        public void onStart() {
                            Timber.d("onStart");
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

                        @Override
                        public void onComplete() {
                            Timber.d("onComplete");
                        }
                    }));
        }
    }

    @Override
    public void getMovieTrailer(int movieId, GetMovieTrailerListener getMovieTrailerListener) {
        if(Constants.MOVIES_API_KEY.isEmpty()) {
            getMovieTrailerListener.onGetMovieTrailerFailure("No API key");
        }
        else {
            compositeDisposable.add(mMovieAPIService.getMovieTrailers(movieId, Constants.MOVIES_API_KEY)
                    .subscribeOn(movieSchedulers.getBackgroundScheduler())
                    .observeOn(movieSchedulers.getUIScheduler())
                    .subscribeWith(new DisposableObserver<Results<Trailer>>() {

                        @Override
                        public void onStart() {
                            Timber.d("onStart");
                        }

                        @Override
                        public void onComplete() {
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
                    }));
        }
    }

    @Override
    public void getMovieReviews(int movieId, MovieReviewsListener movieReviewsListener) {
        if(Constants.MOVIES_API_KEY.isEmpty()) {
            movieReviewsListener.onGetMovieReviewsFailure("No API KEY");
        }
        else {
            compositeDisposable.add(mMovieAPIService.getMovieReview(movieId, Constants.MOVIES_API_KEY)
                    .subscribeOn(movieSchedulers.getBackgroundScheduler())
                    .observeOn(movieSchedulers.getUIScheduler())
                    .subscribeWith(new DisposableObserver<Results<Review>>() {
                        @Override
                        protected void onStart() {
                            Timber.d("onStart");
                        }

                        @Override
                        public void onComplete() {
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
                    }));
        }
    }

    @Override
    public void getMoveActors(int movieId, MovieActorsListener movieActorsListener) {
        if(Constants.MOVIES_API_KEY.isEmpty()) {
            movieActorsListener.onGetMovieActorsFailure("NO API KEY");
        }
        else {
            compositeDisposable.add(mMovieAPIService.getMovieActors(movieId, Constants.MOVIES_API_KEY)
                    .subscribeOn(movieSchedulers.getBackgroundScheduler())
                    .observeOn(movieSchedulers.getUIScheduler())
                    .map(actorCast -> {
                        List<Actor> newActors = new ArrayList<>();
                        /* We only want to get the first 10 actors */
                        for(int i = 0; i < 10; i++) {

                            newActors.add(actorCast.getCast().get(i));
                        }
                        actorCast.getCast().clear();
                        actorCast.getCast().addAll(newActors);

                        return actorCast;
                    })
                    .subscribeWith(new DisposableObserver<Cast<Actor>>() {
                        @Override
                        protected void onStart() {
                            Timber.d("onStart");
                        }

                        @Override
                        public void onComplete() {
                            Timber.d("onCompleted");
                        }

                        @Override
                        public void onError(Throwable e) {
                            Timber.e(e, "onError");
                            movieActorsListener.onGetMovieActorsFailure(e.getMessage());
                        }

                        @Override
                        public void onNext(Cast<Actor> actors) {
                            Timber.d("onNext");
                                movieActorsListener.onGetMovieActorsSuccess(actors);
                        }
                    }));
        }
    }

    @Override
    public void getSimilarMovies(int movieId, SimilarMovieResultsListener similarMovieResultsListener) {
        if(Constants.MOVIES_API_KEY.isEmpty()) {
            similarMovieResultsListener.onSimilarMovieFailure("Empty API Key");
        }
        else {
            compositeDisposable.add(mMovieAPIService.getSimilarMovies(movieId, Constants.MOVIES_API_KEY)
                    .subscribeOn(movieSchedulers.getBackgroundScheduler())
                    .observeOn(movieSchedulers.getUIScheduler())
                    .map(movieResults -> {
                        /* Only display the first ten movies */
                        final List<Movies> movies = new ArrayList<>(10);
                        for(int i = 0; i < 10; i++) {
                            /* Filter on movies to avoid showing terrible or offensive movies */
                            if(movieResults.getResults().get(i).getVote_count() > 350) {
                                movies.add(movieResults.getResults().get(i));
                            }
                        }

                        movieResults.getResults().clear();
                        movieResults.getResults().addAll(movies);

                        return movieResults;
                    })
                    .subscribeWith(new DisposableObserver<Results<Movies>>() {
                        @Override
                        protected void onStart() {
                            Timber.d("onStart");
                        }

                        @Override
                        public void onComplete() {
                            Timber.d("onCompleted");
                        }

                        @Override
                        public void onError(Throwable e) {
                            Timber.e(e, "OnError: %s", e.getMessage());
                            similarMovieResultsListener.onSimilarMovieFailure(e.getMessage());
                        }

                        @Override
                        public void onNext(Results<Movies> moviesResults) {
                            Timber.d("onNext");
                            similarMovieResultsListener.onSimilarMovieSuccess(moviesResults);
                        }
                    }));
        }
    }

    @Override
    public void releaseResources() {
        compositeDisposable.clear();
    }
}
