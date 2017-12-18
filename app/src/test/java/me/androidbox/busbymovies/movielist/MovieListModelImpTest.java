package me.androidbox.busbymovies.movielist;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import javax.inject.Inject;

import di.DaggerTestBusbyMoviesAppComponent;
import io.reactivex.Observable;
import di.TestAndroidModule;
import me.androidbox.busbymovies.models.Movies;
import me.androidbox.busbymovies.models.Results;
import me.androidbox.busbymovies.network.MovieAPIService;
import me.androidbox.busbymovies.utils.Constants;
import me.androidbox.busbymovies.utils.MovieSchedulers;
import okhttp3.ResponseBody;
import retrofit2.Callback;

import static org.mockito.ArgumentMatchers.anyString;
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
    @Mock
    Observable<Results> mockCall;
    @Mock Results<Movies> mockMovies;
    @Mock ResponseBody responseBody;
    @Mock MovieListModelContract.PopularMovieResultsListener mockPopularMoviesResultsListener;
    @Mock MovieListModelContract.MovieSearchResultsListener movieSearchResultsListener;

    @Captor ArgumentCaptor<Callback<List<Results>>> argumentCaptor;

    @Inject
    MovieSchedulers movieSchedulers;

    private MovieListModelContract movieListModelContract;

    @Before
    public void setUp() throws Exception {
        DaggerTestBusbyMoviesAppComponent.builder()
                .testAndroidModule(new TestAndroidModule())
                .build()
                .inject(MovieListModelImpTest.this);

        movieListModelContract = new MovieListModelImp(mockMovieAPIService, movieSchedulers);
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
        final Throwable exception = new Throwable(new Exception("Error"));

        when(mockMovieAPIService.searchMovies(MOVIE_NAME, MOVIE_YEAR, Constants.MOVIES_API_KEY))
                .thenReturn(Observable.error(exception));

        movieListModelContract.searchForMovies(MOVIE_NAME, MOVIE_YEAR, movieSearchResultsListener);

        verify(movieSearchResultsListener).onSearchFailure(exception.getMessage());
        verify(movieSearchResultsListener, never()).onSearchSuccess(mockMovies);
    }
}