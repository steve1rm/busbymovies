package me.androidbox.busbymovies.movielist;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import me.androidbox.busbymovies.models.Movie;
import me.androidbox.busbymovies.models.Movies;
import me.androidbox.busbymovies.models.Results;
import me.androidbox.busbymovies.network.MovieAPIService;
import me.androidbox.busbymovies.utils.Constants;
import me.androidbox.busbymovies.utils.MovieSchedulers;
import timber.log.Timber;

/**
 * Created by steve on 2/19/17.
 */

final public class MovieListModelImp implements MovieListModelContract {

    private MovieAPIService mMovieAPIService;
    private MovieSchedulers movieSchedulers;

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Inject
    public MovieListModelImp(final MovieAPIService movieAPIService, final MovieSchedulers movieSchedulers) {
        this.mMovieAPIService = movieAPIService;
        this.movieSchedulers = movieSchedulers;
    }

    @Override
    public void getPopularMovies(PopularMovieResultsListener popularMovieResultsListener) {
        if(Constants.MOVIES_API_KEY.isEmpty()) {
            popularMovieResultsListener.onPopularMovieFailure("Empty API Key");
        }
        else {
            compositeDisposable.add(mMovieAPIService.getPopular(Constants.MOVIES_API_KEY)
                    .subscribeOn(movieSchedulers.getBackgroundScheduler())
                    .observeOn(movieSchedulers.getUIScheduler())
                    .subscribeWith(new DisposableObserver<Results<Movies>>() {
                        @Override
                        public void onStart() {
                            super.onStart();
                            Timber.d("onStart");
                        }

                        @Override
                        public void onComplete() {
                            Timber.d("onCompleted");
                        }

                        @Override
                        public void onError(Throwable e) {
                            Timber.e(e, "onError %s", e.getMessage());
                            popularMovieResultsListener.onPopularMovieFailure(e.getMessage());
                        }

                        @Override
                        public void onNext(Results<Movies> results) {
                            Timber.d("onNext");
                            popularMovieResultsListener.onPopularMovieSuccess(results);
                        }
                    }));
        }
    }

    @Override
    public void getTopRatedMovies(TopRatedMovieResultsListener topRatedMovieResultsListener) {
        if(Constants.MOVIES_API_KEY.isEmpty()) {
            topRatedMovieResultsListener.onTopRatedMovieFailure("Empty API Key");
        }
        else {
            compositeDisposable.add(mMovieAPIService.getTopRatedMovies(Constants.MOVIES_API_KEY)
                    .subscribeOn(movieSchedulers.getBackgroundScheduler())
                    .observeOn(movieSchedulers.getUIScheduler())
                    .subscribeWith(new DisposableObserver<Results<Movies>>() {
                        @Override
                        public void onComplete() {
                            Timber.d("onCompleted");
                        }

                        @Override
                        public void onError(Throwable e) {
                            topRatedMovieResultsListener.onTopRatedMovieFailure(e.getMessage());
                            Timber.e(e, e.getMessage());
                        }

                        @Override
                        public void onNext(Results<Movies> movies) {
                            topRatedMovieResultsListener.onTopRatedMovieSuccess(movies);
                        }
                    }));
        }
    }

    @Override
    public void getMovieDetail(int movieId, MovieDetailResultsListener movieDetailResultsListener) {
        if(Constants.MOVIES_API_KEY.isEmpty()) {
            movieDetailResultsListener.onFailure("Empty API Key");
        }
        else {
            compositeDisposable.add(mMovieAPIService.getMovieByIdExt(movieId, Constants.MOVIES_API_KEY)
                    .subscribeOn(movieSchedulers.getBackgroundScheduler())
                    .observeOn(movieSchedulers.getUIScheduler())
                    .subscribeWith(new DisposableObserver<Movie>() {
                        @Override
                        public void onComplete() {
                            Timber.d("onCompleted");
                        }

                        @Override
                        public void onError(Throwable e) {
                            Timber.e(e, "onError %s", e.getMessage());
                            movieDetailResultsListener.onFailure(e.getMessage());
                        }

                        @Override
                        public void onNext(Movie movie) {
                            movieDetailResultsListener.onSuccess(movie);
                            Timber.d("onNext");
                        }
                    }));
        }
    }

    @Override
    public void searchForMovies(String movieName, int movieYear, MovieSearchResultsListener movieSearchResultsListener) {
        if(Constants.MOVIES_API_KEY.isEmpty()) {
            movieSearchResultsListener.onSearchFailure("Empty API Key");
        }
        else {
            compositeDisposable.add(mMovieAPIService.searchMovies(movieName, movieYear, Constants.MOVIES_API_KEY)
                    .subscribeOn(movieSchedulers.getBackgroundScheduler())
                    .observeOn(movieSchedulers.getUIScheduler())
                    .subscribeWith(new DisposableObserver<Results<Movies>>() {
                        @Override
                        public void onComplete() {
                            Timber.d("onCompleted");
                        }

                        @Override
                        public void onError(Throwable e) {
                            Timber.e(e, "OnError: %s", e.getMessage());
                            movieSearchResultsListener.onSearchFailure(e.getMessage());
                        }

                        @Override
                        public void onNext(Results<Movies> moviesResults) {
                            Timber.d("onNext");
                            movieSearchResultsListener.onSearchSuccess(moviesResults);
                        }
                    }));
        }
    }

    @Override
    public void releaseResources() {
        Timber.d("releaseResources");
        compositeDisposable.clear();
    }
}