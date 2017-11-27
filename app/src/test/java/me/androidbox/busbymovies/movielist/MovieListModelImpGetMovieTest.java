package me.androidbox.busbymovies.movielist;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.inject.Inject;

import io.reactivex.Observable;
import me.androidbox.busbymovies.models.Movie;
import me.androidbox.busbymovies.network.MovieAPIService;
import me.androidbox.busbymovies.utils.Constants;
import me.androidbox.busbymovies.utils.MovieSchedulers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
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
    @Mock MovieListModelContract.MovieDetailResultsListener mockMovieResultsListener;
    @Mock Call<Movie> mockCall;
    @Mock ResponseBody mockResponseBody;
    @Captor ArgumentCaptor<Callback<Movie>> argumentCaptor;

    @Inject
    MovieSchedulers movieSchedulers;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(MovieListModelImpGetMovieTest.this);

        movieListModelContract = new MovieListModelImp(mockMovieAPIService, movieSchedulers);
        movie = new Movie();
    }

    @Ignore("FIXME")
    @Test
    public void shouldCallSuccessToGetMovies() {
        when(mockMovieAPIService.getMovieById(anyInt(), anyString())).thenReturn(Observable.just(new Movie()));
        Response<Movie> response = Response.success(movie);

        movieListModelContract.getMovieDetail(eq(anyInt()), mockMovieResultsListener);

        verify(mockCall).enqueue(argumentCaptor.capture());
        argumentCaptor.getValue().onResponse(mockCall, response);

        verify(mockMovieResultsListener, times(1)).onSuccess(movie);
        verify(mockMovieResultsListener, never()).onFailure(anyString());
    }

    @Ignore("FIXME")
    @Test
    public void shouldCallFailureOn401Response() {
        when(mockMovieAPIService.getMovieById(anyInt(), anyString())).thenReturn(Observable.just(new Movie()));
        Response<Movie> response = Response.error(401, mockResponseBody);

        movieListModelContract.getMovieDetail(eq(anyInt()), mockMovieResultsListener);

        verify(mockCall).enqueue(argumentCaptor.capture());
        argumentCaptor.getValue().onResponse(mockCall, response);

        verify(mockMovieResultsListener, never()).onSuccess(movie);
        verify(mockMovieResultsListener, times(1)).onFailure(response.message());
    }

    @Ignore("FIXME")
    @Test
    public void shouldCallFailureOn500Response() {
        /* Call the mocked version of the service and return the mock call */
        when(mockMovieAPIService.getMovieById(anyInt(), anyString())).thenReturn(Observable.just(new Movie()));

        /* Setup up mock response with a 500 error */
        Response<Movie> response = Response.error(500, mockResponseBody);

        /* Call the real get movies passing in the mocked interface */
        movieListModelContract.getMovieDetail(eq(anyInt()), mockMovieResultsListener);

        /* verify the response */
        verify(mockCall).enqueue(argumentCaptor.capture());
        argumentCaptor.getValue().onResponse(mockCall, response);

        /* Verify that the correct interface functions where called */
        verify(mockMovieResultsListener, never()).onSuccess(movie);
        verify(mockMovieResultsListener, times(1)).onFailure(response.message());
    }

    @Ignore("FIXME")
    @Test
    public void shouldCallFailureOnRetrofitException() {
        /* Create a exception that will be thrown */
        final Throwable runtimeException = new Throwable(new RuntimeException());
        final int MOVIE_ID = 457387;

        /* when the mocked call for the service return a mock */
        when(mockMovieAPIService.getMovieById(MOVIE_ID, Constants.MOVIES_API_KEY))
                .thenReturn(Observable.error(runtimeException));

        /* Start the actual call to being the test */
        movieListModelContract.getMovieDetail(MOVIE_ID, mockMovieResultsListener);

        /* Capture the argument that is passed to enqueue */
        verify(mockCall).enqueue(argumentCaptor.capture());
        argumentCaptor.getValue().onFailure(mockCall, runtimeException);

        /* Verify correct interface functions where called */
        verify(mockMovieResultsListener, never()).onSuccess(movie);
        verify(mockMovieResultsListener, times(1)).onFailure(runtimeException.getMessage());
    }

    @Ignore("FIXME")
    @Test
    public void shouldSearchForMoviesAndFailOnException() {
        final String MOVIE_NAME = "movie name";
        final int MOVIE_YEAR = 2002;
        final Throwable exception = new Throwable(new Exception());

        when(mockMovieAPIService.getMovieById(MOVIE_YEAR, Constants.MOVIES_API_KEY))
                .thenReturn(Observable.error(exception));

        MovieListModelContract.MovieDetailResultsListener movieSearchResultsListener = mock(MovieListModelContract.MovieDetailResultsListener.class);
        movieListModelContract.getMovieDetail(MOVIE_YEAR, movieSearchResultsListener);

     //   verify(movieSearchResultsListener).onSearchFailure(anyString());
   //     verify(movieSearchResultsListener, never()).onSearchSuccess(mockMovies);
    }
}