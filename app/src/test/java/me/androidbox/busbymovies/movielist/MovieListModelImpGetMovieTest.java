package me.androidbox.busbymovies.movielist;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Collections;

import me.androidbox.busbymovies.models.Movie;
import me.androidbox.busbymovies.models.Results;
import me.androidbox.busbymovies.network.MovieAPIService;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by steve on 2/25/17.
 */
public class MovieListModelImpGetMovieTest {
    private MovieListModelContract movieListModelContract;
    private Results results;
    private Movie movie;

    @Mock MovieAPIService mockMovieAPIService;
    @Mock MovieListModelContract.MovieResultsListener mockMovieResultsListener;
    @Mock Call<Movie> mockCall;
    @Mock ResponseBody mockResponseBody;
    @Captor ArgumentCaptor<Callback<Movie>> argumentCaptor;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(MovieListModelImpGetMovieTest.this);

        movieListModelContract = new MovieListModelImp(mockMovieAPIService);
        movie = new Movie();
    }

    @Test
    public void shouldGetMovies() {
        when(mockMovieAPIService.getMovieById(anyInt(), anyString())).thenReturn(mockCall);
        Response<Movie> response = Response.success(movie);

        movieListModelContract.getMovie(eq(anyInt()), mockMovieResultsListener);

        verify(mockCall).enqueue(argumentCaptor.capture());
        argumentCaptor.getValue().onResponse(null, response);

        verify(mockMovieResultsListener, times(1)).onSuccess(movie);
        verify(mockMovieResultsListener, never()).onFailure(anyString());
    }

}