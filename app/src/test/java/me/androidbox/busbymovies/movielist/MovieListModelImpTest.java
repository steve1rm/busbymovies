package me.androidbox.busbymovies.movielist;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;

import me.androidbox.busbymovies.models.Movie;
import me.androidbox.busbymovies.models.Results;
import me.androidbox.busbymovies.network.MovieAPIService;
import okhttp3.ResponseBody;
import retrofit2.Callback;
import rx.Observable;
import rx.Scheduler;
import rx.android.plugins.RxAndroidPlugins;
import rx.functions.Func1;
import rx.plugins.RxJavaHooks;
import rx.schedulers.Schedulers;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by steve on 2/21/17.
 */
public class MovieListModelImpTest {
    private MovieListPresenterContract movieListPresenterContract;
    private List<Movie> movieList;

    @Mock MovieListViewContract movieListViewContract;
    @Mock MovieAPIService mockMovieAPIService;
    @Mock Observable<Results> mockCall;
    @Mock ResponseBody responseBody;
    private MovieListModelContract movieListModelContract;
    @Captor ArgumentCaptor<Callback<List<Results>>> argumentCaptor;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(MovieListModelImpTest.this);

    //    movieListPresenterContract = new MovieListPresenterImp();
    //    movieList = Collections.singletonList(new Movie());

        movieListModelContract = new MovieListModelImp();

        RxJavaHooks.setOnIOScheduler(new Func1<Scheduler, Scheduler>() {
            @Override
            public Scheduler call(Scheduler scheduler) {
                return Schedulers.immediate();
            }
        });
    }

    @Test
    public void shouldDisplayErrorMessageOnFailure() {
        Results results = new Results();
        when(mockMovieAPIService.getPopular(anyString())).thenReturn(Observable.just(results));

        /* Mock the listener */
        MovieListModelContract.PopularMovieResultsListener mockPopularMoviesResultsListener =
                Mockito.mock(MovieListModelContract.PopularMovieResultsListener.class);

        movieListModelContract.getPopularMovies(mockPopularMoviesResultsListener);

        verify(mockPopularMoviesResultsListener, times(1)).onFailure(anyString());
    }

    @Test
    public void shouldShowErrorWhenFailure() {
        when(mockMovieAPIService.getPopular(anyString())).thenReturn(mockCall);

        mockMovieAPIService.getPopular(anyString());
    }

    @Test
    public void shouldDisplaySuccessWhenNetworkSucceeds() {
        /* Results is the movie results class that is returned */
        Results results = new Results();

        /* Mock the listener */
        MovieListModelContract.PopularMovieResultsListener mockPopularMoviesResultsListener =
                Mockito.mock(MovieListModelContract.PopularMovieResultsListener.class);

        MovieListModelContract mockMovieLIstModelContract = mock(MovieListModelContract.class);
        //  when(mockMovieLIstModelContract.getPopularMovies(mockPopularMoviesResultsListener));

        /* Real instance of the model */
        MovieListModelImp movieListModelImp = new MovieListModelImp();

        /* Call getPopularMovies with mock listener - However, this will still make a real network request */
        movieListModelImp.getPopularMovies(mockPopularMoviesResultsListener);

        /* Verify - but I think I have go this all wrong */
        verify(mockPopularMoviesResultsListener, times(1)).onSuccess(results);
    }

    @Test
    public void shouldShowBooksSuccessfully() {
       // when(movieAPIService.getPopular(anyString())).thenReturn(mockCall);

    }

    @After
    public void tearDown() throws Exception {
        RxJavaHooks.reset();
        RxAndroidPlugins.getInstance().reset();
    }

}