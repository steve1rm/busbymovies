package me.androidbox.busbymovies.movielist;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import me.androidbox.busbymovies.models.Movie;
import me.androidbox.busbymovies.network.MovieAPIService;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static org.mockito.ArgumentMatchers.anyInt;
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
    public void shouldCallSuccessToGetMovies() {
        when(mockMovieAPIService.getMovieById(anyInt(), anyString())).thenReturn(mockCall);
        Response<Movie> response = Response.success(movie);

        movieListModelContract.getMovie(eq(anyInt()), mockMovieResultsListener);

        verify(mockCall).enqueue(argumentCaptor.capture());
        argumentCaptor.getValue().onResponse(mockCall, response);

        verify(mockMovieResultsListener, times(1)).onSuccess(movie);
        verify(mockMovieResultsListener, never()).onFailure(anyString());
    }

    @Test
    public void shouldCallFailureOn401Response() {
        when(mockMovieAPIService.getMovieById(anyInt(), anyString())).thenReturn(mockCall);
        Response<Movie> response = Response.error(401, mockResponseBody);

        movieListModelContract.getMovie(eq(anyInt()), mockMovieResultsListener);

        verify(mockCall).enqueue(argumentCaptor.capture());
        argumentCaptor.getValue().onResponse(mockCall, response);

        verify(mockMovieResultsListener, never()).onSuccess(movie);
        verify(mockMovieResultsListener, times(1)).onFailure(response.message());
    }

    @Test
    public void shouldCallFailureOn500Response() {
        /* Call the mocked version of the service and return the mock call */
        when(mockMovieAPIService.getMovieById(anyInt(), anyString())).thenReturn(mockCall);

        /* Setup up mock response with a 500 error */
        Response<Movie> response = Response.error(500, mockResponseBody);

        /* Call the real get movies passing in the mocked interface */
        movieListModelContract.getMovie(eq(anyInt()), mockMovieResultsListener);

        /* verify the response */
        verify(mockCall).enqueue(argumentCaptor.capture());
        argumentCaptor.getValue().onResponse(mockCall, response);

        /* Verify that the correct interface functions where called */
        verify(mockMovieResultsListener, never()).onSuccess(movie);
        verify(mockMovieResultsListener, times(1)).onFailure(response.message());
    }

    @Test
    public void shouldCallFailureOnRetrofitException() {
        /* when the mocked call for the service return a mock */
        when(mockMovieAPIService.getMovieById(anyInt(), anyString())).thenReturn(mockCall);

        /* Create a exception that will be thrown */
        Throwable runtimeException = new Throwable(new RuntimeException());

        /* Start the actual call to being the test */
        movieListModelContract.getMovie(eq(anyInt()), mockMovieResultsListener);

        /* Capture the argument that is passed to enqueue */
        verify(mockCall).enqueue(argumentCaptor.capture());
        argumentCaptor.getValue().onFailure(mockCall, runtimeException);

        /* Verify correct interface functions where called */
        verify(mockMovieResultsListener, never()).onSuccess(movie);
        verify(mockMovieResultsListener, times(1)).onFailure(runtimeException.getMessage());
    }
}