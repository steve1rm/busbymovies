package me.androidbox.busbymovies.movielist;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import me.androidbox.busbymovies.models.Movies;
import me.androidbox.busbymovies.models.Results;
import me.androidbox.busbymovies.network.MovieAPIService;
import okhttp3.ResponseBody;
import retrofit2.Callback;
import rx.Observable;
import rx.Scheduler;
import rx.android.plugins.RxAndroidPlugins;
import rx.android.plugins.RxAndroidSchedulersHook;
import rx.functions.Func1;
import rx.plugins.RxJavaHooks;
import rx.schedulers.Schedulers;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by steve on 2/21/17.
 */
public class MovieListModelImpTest {

    @Mock MovieAPIService mockMovieAPIService;
    @Mock Observable<Results> mockCall;
    @Mock Results<Movies> mockMovies;
    @Mock ResponseBody responseBody;
    @Mock MovieListModelContract.PopularMovieResultsListener mockPopularMoviesResultsListener;
    @Captor ArgumentCaptor<Callback<List<Results>>> argumentCaptor;

    private MovieListModelContract movieListModelContract;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(MovieListModelImpTest.this);

        movieListModelContract = new MovieListModelImp(mockMovieAPIService);

        RxJavaHooks.setOnIOScheduler(new Func1<Scheduler, Scheduler>() {
            @Override
            public Scheduler call(Scheduler scheduler) {
                return Schedulers.immediate();
            }
        });

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
        when(mockMovieAPIService.getPopular(anyString())).thenReturn(Observable.just(mockMovies));

        movieListModelContract.getPopularMovies(mockPopularMoviesResultsListener);

        verify(mockPopularMoviesResultsListener, never()).onPopularMovieFailure(anyString());
        verify(mockPopularMoviesResultsListener, times(1)).onPopularMovieSuccess(mockMovies);
    }

    @After
    public void tearDown() throws Exception {
        RxJavaHooks.reset();
        RxAndroidPlugins.getInstance().reset();
    }
}