package me.androidbox.busbymovies.moviedetails;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import me.androidbox.busbymovies.models.Results;
import me.androidbox.busbymovies.models.Reviews;
import me.androidbox.busbymovies.network.MovieAPIService;
import okhttp3.ResponseBody;
import rx.Observable;
import rx.Scheduler;
import rx.android.plugins.RxAndroidPlugins;
import rx.android.plugins.RxAndroidSchedulersHook;
import rx.functions.Func1;
import rx.plugins.RxJavaHooks;
import rx.schedulers.Schedulers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyObject;
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
    @Mock Observable<Reviews> mockCall;
    @Mock ResponseBody mockResponseBody;
    @Mock MovieDetailModelContract.MovieReviewsListener mockMovieReviewsListener;
    @Mock Results<Reviews> mockReviews;

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
        Results<Reviews> reviews = new Results<>();

        when(mockMovieAPIService.getMovieReview(anyInt(), anyString()))
                .thenReturn(Observable.just(eq(reviews)));

        /* Actual call to get movie reviews */
        mMovieDetailModelContract.getMovieReviews(anyInt(), mockMovieReviewsListener);

        /* Verify that the result was correct */
        verify(mockMovieReviewsListener, times(1)).onGetMovieReviewsSuccess(reviews);
        verify(mockMovieReviewsListener, never()).onGetMovieReviewsFailure(anyString());
    }

    @After
    public void tearDown() throws Exception {
        RxJavaHooks.reset();
        RxAndroidPlugins.getInstance().reset();
    }

}