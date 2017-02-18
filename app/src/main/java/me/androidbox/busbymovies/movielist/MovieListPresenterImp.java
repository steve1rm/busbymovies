package me.androidbox.busbymovies.movielist;

/**
 * Created by steve on 2/18/17.
 */

public class MovieListPresenterImp implements MovieListPresenterContract<MovieListViewImp> {
    private MovieListViewContract movieListViewContract;

    /**
     * Attach the view to the presenter
     * @param movieListView
     */
    @Override
    public void attachView(MovieListViewImp movieListView) {
        movieListViewContract = movieListView;
    }

    @Override
    public void detachView() {
        movieListViewContract = null;
    }

    @Override
    public void retrieveMovies() {

    }
}
