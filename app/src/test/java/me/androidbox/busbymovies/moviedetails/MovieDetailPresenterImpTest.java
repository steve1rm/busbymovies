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
import me.androidbox.busbymovies.models.Movies;
import me.androidbox.busbymovies.models.Results;

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
}