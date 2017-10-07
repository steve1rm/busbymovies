package me.androidbox.busbymovies.movielist;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import me.androidbox.busbymovies.models.Movies;
import me.androidbox.busbymovies.models.Results;
import me.androidbox.busbymovies.network.MovieAPIService;
import me.androidbox.busbymovies.utils.Constants;
import okhttp3.ResponseBody;
import retrofit2.Callback;
import rx.Observable;
import rx.Scheduler;
import rx.android.plugins.RxAndroidPlugins;
import rx.android.plugins.RxAndroidSchedulersHook;
import rx.plugins.RxJavaHooks;
import rx.schedulers.Schedulers;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by steve on 2/21/17.
 */
@RunWith(MockitoJUnitRunner.class)
public class MovieListModelImpTest {

    @Mock MovieAPIService mockMovieAPIService;
    @Mock Observable<Results> mockCall;
    @Mock Results<Movies> mockMovies;
    @Mock ResponseBody responseBody;
    @Mock MovieListModelContract.PopularMovieResultsListener mockPopularMoviesResultsListener;
    @Mock MovieListModelContract.MovieSearchResultsListener movieSearchResultsListener;

    @Captor ArgumentCaptor<Callback<List<Results>>> argumentCaptor;

    private MovieListModelContract movieListModelContract;

    @Before
    public void setUp() throws Exception {
        movieListModelContract = new MovieListModelImp(mockMovieAPIService);

        RxJavaHooks.setOnIOScheduler(scheduler -> Schedulers.immediate());

        /* Override RxAndroid schedulers */
        final RxAndroidPlugins rxAndroidPlugins = RxAndroidPlugins.getInstance();
        rxAndroidPlugins.registerSchedulersHook(new RxAndroidSchedulersHook() {
            @Override
            public Scheduler getMainThreadScheduler() {
                return Schedulers.immediate();
            }
        });
    }

    @Test
    public void shouldDisplayFailureMessageOnError() {
        when(mockMovieAPIService.getPopular(anyString()))
                .thenReturn(Observable.error(new Throwable(new RuntimeException())));

        movieListModelContract.getPopularMovies(mockPopularMoviesResultsListener);

        verify(mockPopularMoviesResultsListener, times(1)).onPopularMovieFailure(anyString());
        verify(mockPopularMoviesResultsListener, never()).onPopularMovieSuccess(mockMovies);
    }

    @Test
    public void shouldDisplaySuccessMessageOnSuccess() {
        when(mockMovieAPIService.getPopular(anyString()))
                .thenReturn(Observable.just(mockMovies));

        movieListModelContract.getPopularMovies(mockPopularMoviesResultsListener);

        verify(mockPopularMoviesResultsListener, never()).onPopularMovieFailure(anyString());
        verify(mockPopularMoviesResultsListener, times(1)).onPopularMovieSuccess(mockMovies);
    }

    @Test
    public void testSearchMoviesReturnsMoviesOnSuccess() {
        final String MOVIE_NAME = "movie name";
        final int MOVIE_YEAR = 2002;
        final Throwable exception = new Throwable(new Exception());

        when(mockMovieAPIService.searchMovies(MOVIE_NAME, MOVIE_YEAR, Constants.MOVIES_API_KEY))
                .thenReturn(Observable.just(mockMovies));

        movieListModelContract.searchForMovies(MOVIE_NAME, MOVIE_YEAR, movieSearchResultsListener);

        verify(movieSearchResultsListener).onSearchSuccess(mockMovies);
        verify(movieSearchResultsListener, never()).onSearchFailure(anyString());
    }

    @Test
    public void shouldSearchForMoviesAndFailOnException() {
        final String MOVIE_NAME = "movie name";
        final int MOVIE_YEAR = 2002;
        final Throwable exception = new Throwable(new Exception());

        when(mockMovieAPIService.searchMovies(MOVIE_NAME, MOVIE_YEAR, Constants.MOVIES_API_KEY))
                .thenReturn(Observable.error(exception));

        movieListModelContract.searchForMovies(MOVIE_NAME, MOVIE_YEAR, movieSearchResultsListener);

        verify(movieSearchResultsListener).onSearchFailure(anyString());
        verify(movieSearchResultsListener, never()).onSearchSuccess(mockMovies);
    }


    @Test
    public void testGetSimilarMoviesSuccessInGettingSimilarMovies() {

    }



    @Test
    public void testGetSimilarMoviesFailsToGetSimilarMoviesOnException() {
        final Throwable exception = new Throwable(new Exception());
        final int movieId = 123456;
        final MovieListModelContract.SimilarMovieResultsListener similarMovieResultsListener = Mockito.mock(MovieListModelContract.SimilarMovieResultsListener.class);
        final Results<Movies> moviesResults = new Results<>();

        when(mockMovieAPIService.getSimilarMovies(movieId, Constants.MOVIES_API_KEY))
                .thenReturn(Observable.error(exception));

        movieListModelContract.getSimilarMovies(movieId, similarMovieResultsListener);

        verify(similarMovieResultsListener).onSimilarMovieFailure(anyString());
        verify(similarMovieResultsListener, never()).onSimilarMovieSuccess(moviesResults);
    }































    @After
    public void tearDown() throws Exception {
        RxJavaHooks.reset();
        RxAndroidPlugins.getInstance().reset();
    }
}