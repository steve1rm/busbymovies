package me.androidbox.busbymovies.movielist;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import me.androidbox.busbymovies.models.Movie;
import me.androidbox.busbymovies.models.Results;
import me.androidbox.busbymovies.network.MovieAPIService;
import okhttp3.ResponseBody;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;

import static org.mockito.ArgumentMatchers.anyString;
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
    @Mock MovieAPIService movieAPIService;
    @Mock Observable<Results> mockCall;
    @Mock ResponseBody responseBody;
    @Captor ArgumentCaptor<Callback<List<Results>>> argumentCaptor;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(MovieListModelImpTest.this);
        movieListPresenterContract = new MovieListPresenterImp();
        movieList = Collections.singletonList(new Movie());
    }

    @Test
    public void shouldShowErrorWhenFailure() {
        when(movieAPIService.getPopular(anyString())).thenReturn(mockCall);

        movieAPIService.getPopular(anyString());

    }


    @Test
    public void shouldDisplaySuccessWhenNetworkSucceeds() {
        /* Results is the movie results class that is returned */
        Results results = new Results();

        /* Mock the listener */
        MovieListModelContract.PopularMovieResultsListener mockPopularMoviesResultsListener =
                Mockito.mock(MovieListModelContract.PopularMovieResultsListener.class);

        /* Real instance of the model */
        MovieListModelImp movieListModelImp = new MovieListModelImp();

        /* Call getPopularMovies with mock listener - However, this will still make a real network request */
        movieListModelImp.getPopularMovies(mockPopularMoviesResultsListener);

        /* Verify - but I think I have go this all wrong */
        verify(mockPopularMoviesResultsListener, times(1)).onSuccess(results);
    }

    @Test
    public void shouldShowBooksSuccessfully() {
        when(movieAPIService.getPopular(anyString())).thenReturn(mockCall);

    }

    @After
    public void tearDown() throws Exception {

    }

}