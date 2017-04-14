package me.androidbox.busbymovies.moviedetails;

import org.assertj.core.api.exception.RuntimeIOException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import me.androidbox.busbymovies.models.Results;
import me.androidbox.busbymovies.models.Review;
import me.androidbox.busbymovies.models.Trailer;
import me.androidbox.busbymovies.network.MovieAPIService;
import okhttp3.ResponseBody;
import rx.Observable;
import rx.Scheduler;
import rx.android.plugins.RxAndroidPlugins;
import rx.android.plugins.RxAndroidSchedulersHook;
import rx.plugins.RxJavaHooks;
import rx.schedulers.Schedulers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by steve on 4/9/17.
 */
public class MovieDetailModelImpTest {

    @Mock MovieAPIService mockMovieAPIService;
    @Mock Observable<Review> mockCall;
    @Mock ResponseBody mockResponseBody;
    @Mock MovieDetailModelContract.MovieReviewsListener mockMovieReviewsListener;
    @Mock MovieDetailModelContract.GetMovieTrailerListener mockMovieTrailerListener;
    @Mock Results<Review> mockReviews;
    @Mock Results<Trailer> mockTrailers;

    private MovieDetailModelContract mMovieDetailModelContract;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(MovieDetailModelImpTest.this);

        mMovieDetailModelContract = new MovieDetailModelImp(mockMovieAPIService);

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
    public void shouldDisplayErrorWhenFailureToGetReviews() throws Exception {
        /* Stub the call to get movie reviews */
        when(mockMovieAPIService.getMovieReview(anyInt(), anyString()))
                .thenReturn(Observable.error(new Throwable(new RuntimeException(eq("Failed to get movie reviews")))));

        /* Actual call to get movie reviews */
        mMovieDetailModelContract.getMovieReviews(anyInt(), mockMovieReviewsListener);

        /* Verify that the result was correct */
        verify(mockMovieReviewsListener, times(1)).onGetMovieReviewsFailure(anyString());
        verify(mockMovieReviewsListener, never()).onGetMovieReviewsSuccess(mockReviews);
    }

    @Test
    public void shouldDisplaySuccessWhenReviewsRetrieved() throws Exception {
        /* Stub the method call for getting reviews */
        when(mockMovieAPIService.getMovieReview(anyInt(), anyString()))
                .thenReturn(Observable.just(eq(mockReviews)));

        /* Actual call to get movie reviews */
        mMovieDetailModelContract.getMovieReviews(anyInt(), mockMovieReviewsListener);

        /* Verify that the result was correct */
        verify(mockMovieReviewsListener, times(1)).onGetMovieReviewsSuccess(mockReviews);
        verify(mockMovieReviewsListener, never()).onGetMovieReviewsFailure(anyString());
    }

    @Test
    public void shouldDisplayErrorWhenFailureToGetTrailers() throws Exception {
        /* Stub the function that gets the movie trailers */
        when(mockMovieAPIService.getMovieTrailers(anyInt(), anyString()))
                .thenReturn(Observable.error(new Throwable(new RuntimeIOException(eq("Failed to get movie trailers")))));

        /* Actual call */
        mMovieDetailModelContract.getMovieTrailer(anyInt(), mockMovieTrailerListener);

        /* Verify the results */
        verify(mockMovieTrailerListener, times(1)).onGetMovieTrailerFailure(anyString());
        verify(mockMovieTrailerListener, never()).onGetMovieTrailerSuccess(mockTrailers);
    }
/*

    @Test
    public void shouldDisplaySuccessMessageOnSuccess() {
        when(mockMovieAPIService.getPopular(anyString()))
                 .thenReturn(Observable.just(mockMovies));

        movieListModelContract.getPopularMovies(mockPopularMoviesResultsListener);

        verify(mockPopularMoviesResultsListener, never()).onPopularMovieFailure(anyString());
        verify(mockPopularMoviesResultsListener, times(1)).onPopularMovieSuccess(mockMovies);
    }
*/

    @Test
    public void shouldGetAllMovieTrailersOnSuccessfulCall() throws Exception {
        /* Stub the function that will get the movie trailers */
        when(mockMovieAPIService.getMovieTrailers(anyInt(), anyString()))
                .thenReturn(Observable.just(eq(mockTrailers)));

        /* The actual call to get the movie trailers */
        mMovieDetailModelContract.getMovieTrailer(anyInt(), mockMovieTrailerListener);

        /* Verify the results are correct */
        verify(mockMovieTrailerListener, times(1)).onGetMovieTrailerSuccess(mockTrailers);
        verify(mockMovieTrailerListener, never()).onGetMovieTrailerFailure(anyString());
    }

    @After
    public void tearDown() throws Exception {
        RxJavaHooks.reset();
        RxAndroidPlugins.getInstance().reset();
    }

}