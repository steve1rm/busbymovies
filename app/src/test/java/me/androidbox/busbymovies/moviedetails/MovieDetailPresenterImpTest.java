package me.androidbox.busbymovies.moviedetails;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import me.androidbox.busbymovies.models.Actor;
import me.androidbox.busbymovies.models.Cast;
import me.androidbox.busbymovies.models.Movie;
import me.androidbox.busbymovies.models.Movies;
import me.androidbox.busbymovies.models.Results;
import me.androidbox.busbymovies.models.Review;
import me.androidbox.busbymovies.models.Trailer;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

/**
 * Created by steve on 10/7/17.
 */
@RunWith(MockitoJUnitRunner.class)
public class MovieDetailPresenterImpTest {
    private MovieDetailPresenterImp movieDetailPresenter;

    @Mock MovieDetailViewContract movieDetailViewContract;
    @Mock MovieDetailModelContract movieDetailModelContract;

    @Before
    public void setup() {
        movieDetailPresenter = new MovieDetailPresenterImp(movieDetailModelContract);
        movieDetailPresenter.attachView(movieDetailViewContract);
    }

    @Test
    public void testGetSimilarMovies() {
        final int movieId = 12345;

        movieDetailPresenter.getSimilarMovies(movieId);

        verify(movieDetailModelContract).getSimilarMovies(movieId, movieDetailPresenter);
    }

    @Test
    public void testOnSimilarMovieFailure_isCalledWhenOnFailure() {
        final String errorMessage = "Error Message";

        movieDetailPresenter.onSimilarMovieFailure(errorMessage);

        verify(movieDetailViewContract).failedToGetSimilarMovies(errorMessage);
    }

    @Test
    public void testOnSimilarMovieSuccess_isCallWhenOnSuccess() {
        final Results<Movies> moviesResults = new Results<>();

        movieDetailPresenter.onSimilarMovieSuccess(moviesResults);

        verify(movieDetailViewContract).successToGetSimilarMovies(moviesResults);
    }

    @Test
    public void testOnGetMovieActorsFailure_verifyErrorMessage() {
        final String ERROR_MESSAGE = "error message";

        movieDetailPresenter.onGetMovieActorsFailure(ERROR_MESSAGE);

        verify(movieDetailViewContract).failedToReceiveMovieActors(ERROR_MESSAGE);
        verifyNoMoreInteractions(movieDetailModelContract);
    }

    @Test
    public void testOnMovieActorsSuccess_actorsList() {
        final List<Actor> actors = new ArrayList<>();
        final Actor actor = new Actor("picture_path", "actor name", "character");
        actors.add(actor);
        final Cast<Actor> actorCast = new Cast<>(actors);

        movieDetailPresenter.onGetMovieActorsSuccess(actorCast);

        verify(movieDetailViewContract).successToReceiveMovieActors(actorCast);
        verifyNoMoreInteractions(movieDetailModelContract);
    }

    @Test
    public void testRequestMovieActors_doNothingIfMovieDetailModelContract_isNullValue() {
        final MovieDetailPresenterImp movieDetailPresenterImp = new MovieDetailPresenterImp(null);

        movieDetailPresenterImp.requestMovieActors(12345);

        verifyNoMoreInteractions(movieDetailModelContract);
    }

    @Test
    public void testRequestMovieActors_getMovieActors_whenNonNullValue() {
        movieDetailPresenter.requestMovieActors(12345);

        verify(movieDetailModelContract).getMoveActors(12345, movieDetailPresenter);
        verifyNoMoreInteractions(movieDetailModelContract);
    }

    @Test
    public void testRequestMovieReview_doNothing_whenNullValue() {
        final MovieDetailPresenterImp movieDetailPresenterImp = new MovieDetailPresenterImp(null);

        movieDetailPresenterImp.requestMovieReviews(12345);

        verifyNoMoreInteractions(movieDetailModelContract);
    }

    @Test
    public void testRequestMovieReview_getMovieReview() {
        movieDetailPresenter.requestMovieReviews(12345);

        verify(movieDetailModelContract).getMovieReviews(12345, movieDetailPresenter);
        verifyNoMoreInteractions(movieDetailModelContract);
    }

    @Test
    public void testOnGetMovieReviewsFailure_doNothing_whenNullValue() {
        movieDetailPresenter.detachView();

        movieDetailPresenter.onGetMovieReviewsFailure("Error Message");

        verifyNoMoreInteractions(movieDetailViewContract);
    }

    @Test
    public void testOnGetMovieReviewsFailure_sendErrorMessage_whenNonNullValue() {
        movieDetailPresenter.onGetMovieReviewsFailure("Error Message");

        verify(movieDetailViewContract).failedToReceiveMovieReviews("Error Message");
        verifyNoMoreInteractions(movieDetailViewContract);
    }

    @Test
    public void testOnGetMovieReviewsSuccess_doNothing_whenNullValue() {
        movieDetailPresenter.detachView();
        final Results<Review> reviewList = new Results<>();

        movieDetailPresenter.onGetMovieReviewsSuccess(reviewList);

        verifyNoMoreInteractions(movieDetailViewContract);
    }

    @Test
    public void testOnGetMovieReviewsSuccess_sendErrorMessage_whenNonNullValue() {
        final Results<Review> reviewList = new Results<>();

        movieDetailPresenter.onGetMovieReviewsSuccess(reviewList);

        verify(movieDetailViewContract).receivedMovieReviews(reviewList);
        verifyNoMoreInteractions(movieDetailViewContract);
    }

    @Test
    public void testOnGetMovieTrailerFailure_doNothing_whenNullValue() {
        movieDetailPresenter.detachView();

        movieDetailPresenter.onGetMovieTrailerFailure("Error Message");

        verifyNoMoreInteractions(movieDetailViewContract);
    }

    @Test
    public void testOnGetMovieTrailerFailure_sendErrorMessage_whenNonNullValue() {
        movieDetailPresenter.onGetMovieTrailerFailure("Error Message");

        verify(movieDetailViewContract).failedToGetMovieTrailers("Error Message");
        verifyNoMoreInteractions(movieDetailViewContract);
    }

    @Test
    public void testOnGetMovieTrailerSuccess_doNothing_whenNullValue() {
        movieDetailPresenter.detachView();
        final Results<Trailer> trailerResults = new Results<>();

        movieDetailPresenter.onGetMovieTrailerSuccess(trailerResults);

        verifyNoMoreInteractions(movieDetailViewContract);
    }

    @Test
    public void testOnGetMovieTrailerSuccess_sendMovieTrailers_whenNonNullValue() {
        final Results<Trailer> trailerResults = new Results<>();

        movieDetailPresenter.onGetMovieTrailerSuccess(trailerResults);

        verify(movieDetailViewContract).receivedMovieTrailers(trailerResults);
        verifyNoMoreInteractions(movieDetailViewContract);
    }

    @Test
    public void testRequestMovieTrailer_doNothing_whenNullValue() {
        final MovieDetailPresenterImp movieDetailPresenterImp = new MovieDetailPresenterImp(null);

        movieDetailPresenterImp.requestMovieTrailer(12345);

        verifyNoMoreInteractions(movieDetailViewContract);
    }

    @Test
    public void testRequestMovieTrailer_getMovieTrailer_whenNonNullValue() {
        final MovieDetailPresenterImp movieDetailPresenterImp = new MovieDetailPresenterImp(movieDetailModelContract);

        movieDetailPresenterImp.requestMovieTrailer(12345);

        verify(movieDetailModelContract).getMovieTrailer(12345, movieDetailPresenterImp);
        verifyNoMoreInteractions(movieDetailModelContract);
    }

    @Test
    public void testOnGetMovieDetailSuccess_doNothing_whenNullValue() {
        movieDetailPresenter.detachView();
        final Movie movie = new Movie();

        movieDetailPresenter.onGetMovieDetailSuccess(movie);

        verifyNoMoreInteractions(movieDetailViewContract);
    }

    @Test
    public void testOnGetMovieDetailSuccess_displayMovieDetails_whenNonNullValue() {
        final Movie movie = new Movie();

        movieDetailPresenter.onGetMovieDetailSuccess(movie);

        verify(movieDetailViewContract).displayMovieDetails(movie);
        verifyNoMoreInteractions(movieDetailViewContract);
    }

    @Test
    public void testOnGetMovieDetailFailure_doNothing_whenNullValue() {
        movieDetailPresenter.detachView();

        movieDetailPresenter.onGetMovieDetailFailure("Error Message");

        verifyNoMoreInteractions(movieDetailViewContract);
    }

    @Test
    public void testOnGetMovieDetailFailure_displayErrorFailedToGetMovie_whenNonNullValue() {
        movieDetailPresenter.onGetMovieDetailFailure("Error Message");

        verify(movieDetailViewContract).displayErrorFailedToGetMovie("Error Message");
        verifyNoMoreInteractions(movieDetailViewContract);
    }

    @Test
    public void testGetMovieDetail_getMovieDetail() {
        movieDetailPresenter.getMovieDetail(12345);

        verify(movieDetailModelContract).getMovieDetail(12345, movieDetailPresenter);
        verifyNoMoreInteractions(movieDetailModelContract);
    }

    @Test
    public void testDetachView_doNothing_whenNullValue() {
        movieDetailPresenter.detachView();

        verify(movieDetailModelContract).releaseResources();
        verifyNoMoreInteractions(movieDetailModelContract);

        movieDetailPresenter.detachView();
        verifyNoMoreInteractions(movieDetailModelContract);
    }

    @Test
    public void testGetVoteAverage_calculatesCorrectVote() {
        assertThat(movieDetailPresenter.getVoteAverage(7.4F), is(3.7F));
    }

    @Test
    public void testGetMovieFormattedDate_formatsDate() {
        assertThat(movieDetailPresenter.getMovieFormattedDate(
                "20 Dec 2017", "dd-mmm-yyyy"), is("20 Dec 2017"));
    }
}