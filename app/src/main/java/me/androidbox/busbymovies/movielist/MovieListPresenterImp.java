package me.androidbox.busbymovies.movielist;

/**
 * Created by steve on 2/18/17.
 */

public class MovieListPresenterImp implements MovieListPresenterContract<MovieListViewImp>, MovieListModelContract.MovieResultsListener {
    private MovieListViewContract mMovieListViewContract;
    private MovieListModelContract mMovieModelContract;

    public MovieListPresenterImp() {

    }

    /**
     * Attach the view to the presenter
     * @param movieListView
     */
    @Override
    public void attachView(MovieListViewImp movieListView) {
        mMovieListViewContract = movieListView;
    }

    @Override
    public void detachView() {
        mMovieListViewContract = null;
    }

    @Override
    public void getPopularMovies() {
        if(mMovieListViewContract != null) {
            mMovieModelContract.getPopularMovies(MovieListPresenterImp.this);
        }
    }

    @Override
    public void getTopRatedMovies() {
        if(mMovieListViewContract != null) {
            mMovieListViewContract.displayTopRatedMovies("top rated");
        }
    }

    /**
     * Wait for the response to be called back in the Model on failure
     */
    @Override
    public void onFailure() {
        mMovieListViewContract.displayPopularMovies("popular");
    }

    /**
     * Wait for the response to be called back in the model on success
     */
    @Override
    public void onSuccess() {
        mMovieListViewContract.displayPopularMovies("popular");
    }
}
