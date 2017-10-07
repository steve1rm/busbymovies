package me.androidbox.busbymovies.movielist;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import me.androidbox.busbymovies.models.Movies;
import me.androidbox.busbymovies.models.Results;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by steve on 2/22/17.
 */
public class MovieListPresenterImpTest {

    private MovieListModelContract mockMovieListModelContract;
    private MovieListViewContract mockMovieListViewContract;
    private MovieListPresenterImp movieListPresenterContract;

    @Before
    public void setUp() throws Exception {
        mockMovieListModelContract = mock(MovieListModelContract.class);
        mockMovieListViewContract = mock(MovieListViewContract.class);

        movieListPresenterContract = new MovieListPresenterImp(mockMovieListModelContract);
        movieListPresenterContract.attachView(mockMovieListViewContract);
    }

    @Test
    public void shouldFailToAttachViewIfTheViewIsNull() {
        movieListPresenterContract.attachView(mockMovieListViewContract);
        movieListPresenterContract.getPopularMovies();

        verify(mockMovieListModelContract, times(0)).getPopularMovies(null);
    }

    @Test
    public void shouldReleaseModelResourcesWhenDetached() {
        doNothing().when(mockMovieListModelContract).releaseResources();

        movieListPresenterContract.detachView();

        verify(mockMovieListModelContract, times(1)).releaseResources();
    }

    @Test
    public void testSearchMoviesOnSuccess() {
        final String MOVIE_NAME = "movie name";
        final int MOVIE_YEAR = 2002;

        movieListPresenterContract.searchMovies(MOVIE_NAME, MOVIE_YEAR);

        verify(mockMovieListModelContract).searchForMovies(eq(MOVIE_NAME), eq(MOVIE_YEAR), any());
    }

    @Test
    public void testSearchMovieOnSuccess() {
        final Results<Movies> movies = new Results<>();

        movieListPresenterContract.onSearchSuccess(movies);

        verify(mockMovieListViewContract).successToGetSearchMovies(movies);
    }

    @Test
    public void testSearchMovieOnFailureWhenFailedToGetMovies() {
        final String ERROR_MESSAGE = "error_message";

        movieListPresenterContract.onSearchFailure(ERROR_MESSAGE);

        verify(mockMovieListViewContract).failedToGetSearchMovies(ERROR_MESSAGE);
    }

    @Ignore("FIXME")
    @Test
    public void shouldAttachViewWhenViewIsNotNull() {
        movieListPresenterContract.attachView(mockMovieListViewContract);
        movieListPresenterContract.getPopularMovies();
        MovieListModelContract.PopularMovieResultsListener mockPopularMoviesResultsListener =
                mock(MovieListModelContract.PopularMovieResultsListener.class);

        verify(mockMovieListModelContract, times(1)).getPopularMovies(null);
    }

    @Ignore("FIXME pass in listener")
    @Test
    public void testGetSimilarMovies() {
        final int movieId = 12345;
        final MovieListModelContract.SimilarMovieResultsListener similarMovieResultsListener = new MovieListModelContract.SimilarMovieResultsListener() {
            @Override
            public void onSimilarMovieFailure(String errorMessage) {

            }

            @Override
            public void onSimilarMovieSuccess(Results<Movies> similarMovies) {

            }
        };

        movieListPresenterContract.getSimilarMovies(movieId);

        verify(mockMovieListModelContract).getSimilarMovies(movieId, null);
    }

    @Test
    public void testOnSimilarMovieFailure_isCalledWhenOnFailure() {
        final String errorMessage = "Error Message";

        movieListPresenterContract.onSimilarMovieFailure(errorMessage);

        verify(mockMovieListViewContract).failedToGetSimilarMovies(errorMessage);
    }

    @Test
    public void testOnSimilarMovieSuccess_isCallWhenOnSuccess() {
        final Results<Movies> moviesResults = new Results<>();

        movieListPresenterContract.onSimilarMovieSuccess(moviesResults);

        verify(mockMovieListViewContract).successToGetSimilarMovies(moviesResults);
    }
}