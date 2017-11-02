package me.androidbox.busbymovies.moviedetails;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import io.reactivex.Observable;
import me.androidbox.busbymovies.models.Movies;
import me.androidbox.busbymovies.models.Results;
import me.androidbox.busbymovies.models.Review;
import me.androidbox.busbymovies.models.Trailer;
import me.androidbox.busbymovies.network.MovieAPIService;
import me.androidbox.busbymovies.utils.Constants;
import me.androidbox.busbymovies.utils.MovieSchedulers;
import okhttp3.ResponseBody;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by steve on 4/9/17.
 */
public class MovieDetailModelImpTest {

    @Mock MovieAPIService mockMovieAPIService;
    @Mock MovieSchedulers movieSchedulers;
    @Mock Observable<Review> mockCall;
    @Mock ResponseBody mockResponseBody;
    @Mock MovieDetailModelContract.MovieReviewsListener mockMovieReviewsListener;
    @Mock MovieDetailModelContract.GetMovieTrailerListener mockMovieTrailerListener;
    @Mock Results<Review> mockReviews;
    @Mock Results<Trailer> mockTrailers;

    private MovieDetailModelContract mMovieDetailModelContract;

    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(MovieDetailModelImpTest.this);

        mMovieDetailModelContract = new MovieDetailModelImp(mockMovieAPIService, movieSchedulers);
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
        verify(mockMovieReviewsListener, times(1)).onGetMovieReviewsSuccess(null);
        verify(mockMovieReviewsListener, never()).onGetMovieReviewsFailure(anyString());
    }

    @Test
    public void shouldDisplayErrorWhenFailureToGetTrailers() throws Exception {
        /* Stub the function that gets the movie trailers */
        when(mockMovieAPIService.getMovieTrailers(anyInt(), anyString()))
                .thenReturn(Observable.error(new Throwable(new RuntimeException(eq("Failed to get movie trailers")))));

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
        verify(mockMovieTrailerListener, times(1)).onGetMovieTrailerSuccess(null);
        verify(mockMovieTrailerListener, never()).onGetMovieTrailerFailure(anyString());
    }


    @Test
    public void testGetSimilarMoviesSuccessInGettingSimilarMovies() {
        final Throwable exception = new Throwable(new Exception("Error"));
        final int movieId = 123456;
        final MovieDetailModelContract.SimilarMovieResultsListener similarMovieResultsListener = Mockito.mock(MovieDetailModelContract.SimilarMovieResultsListener.class);
        final Results<Movies> moviesResults = new Results<>();

        when(mockMovieAPIService.getSimilarMovies(movieId, Constants.MOVIES_API_KEY))
                .thenReturn(Observable.error(exception));

        mMovieDetailModelContract.getSimilarMovies(movieId, similarMovieResultsListener);

        verify(similarMovieResultsListener).onSimilarMovieFailure(exception.getMessage());
        verify(similarMovieResultsListener, never()).onSimilarMovieSuccess(moviesResults);
    }

    @Test
    public void testGetSimilarMoviesFailsToGetSimilarMoviesOnException() {
        final Throwable exception = new Throwable(new Exception());
        final int movieId = 123456;
        final MovieDetailModelContract.SimilarMovieResultsListener similarMovieResultsListener = Mockito.mock(MovieDetailModelContract.SimilarMovieResultsListener.class);
        final Results<Movies> moviesResults = new Results<>();

        when(mockMovieAPIService.getSimilarMovies(movieId, Constants.MOVIES_API_KEY))
                .thenReturn(Observable.error(exception));

        mMovieDetailModelContract.getSimilarMovies(movieId, similarMovieResultsListener);

        verify(similarMovieResultsListener).onSimilarMovieFailure(anyString());
        verify(similarMovieResultsListener, never()).onSimilarMovieSuccess(moviesResults);
    }
}