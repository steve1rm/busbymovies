package me.androidbox.busbymovies.movielist;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.util.Collections;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import me.androidbox.busbymovies.R;
import me.androidbox.busbymovies.adapters.MovieAdapter;
import me.androidbox.busbymovies.data.MovieFavouritePresenterContract;
import me.androidbox.busbymovies.di.BusbyMoviesMainApplication;
import me.androidbox.busbymovies.models.Movie;
import me.androidbox.busbymovies.models.Movies;
import me.androidbox.busbymovies.models.Results;
import me.androidbox.busbymovies.moviesearch.MovieSearchDialog;
import me.androidbox.busbymovies.moviesearch.MovieSearchListener;
import timber.log.Timber;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieListViewImp extends Fragment implements MovieListViewContract,
        MovieFavouritePresenterContract.DbOperationsListener, MovieSearchListener {
    public static final String TAG = MovieListViewImp.class.getSimpleName();

    @Inject MovieListPresenterContract<MovieListViewContract> mMovieListPresenterImp;
    @Inject
    MovieFavouritePresenterContract mMovieFavouritePresenterImp;

    @BindView(R.id.rvMovieList) RecyclerView mRvMovieList;
    @BindView(R.id.pbMovieList) ContentLoadingProgressBar mPbMovieList;
    @BindView(R.id.fabPopular) FloatingActionButton mFabPopular;
    @BindView(R.id.fabTopRated) FloatingActionButton mFabTopRated;
    @BindView(R.id.fabFavourite) FloatingActionButton mFabFavourite;
    @BindView(R.id.swipeContainer) SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.fabSearch) FloatingActionButton mFabSearch;

    private Unbinder mUnbinder;
    private MovieAdapter mMovieAdapter;
    private boolean mIsSortFabOpen;

    public MovieListViewImp() {
        // Required empty public constructor
    }

    /* Factory method */
    public static MovieListViewImp newInstance() {
        return new MovieListViewImp();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.movie_list_view, container, false);

        mUnbinder = ButterKnife.bind(MovieListViewImp.this, view);

        setupRecyclerView();
        setupSwipeToRefresh();

        /* Hide the progress bar */
        hideProgressBar();

        if(mIsSortFabOpen) {
            mIsSortFabOpen = false;
        }

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ((BusbyMoviesMainApplication)getActivity().getApplication())
                .getMovieListComponent()
                .inject(this);

        if(mMovieListPresenterImp != null) {
            Timber.d("mMovieListPresenterImp != null");
            mMovieListPresenterImp.attachView(MovieListViewImp.this);
            getPopularMovies();

            if(mMovieFavouritePresenterImp != null) {
                Timber.d("mMovieFavouritePresenterImp != null");
                mMovieFavouritePresenterImp.getFavouriteMovies(MovieListViewImp.this);
            }
        }
        else {
            Timber.e("mMovieListPresenterImp == null");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Timber.d("onDestroyView");

        /* close the fab button if open */
        if(mIsSortFabOpen) {
            mIsSortFabOpen = false;
/*
            mFabPopular.setVisibility(View.INVISIBLE);
            mFabTopRated.setVisibility(View.INVISIBLE);
*/
        }

        mMovieListPresenterImp.detachView();
        mUnbinder.unbind();
    }

    /* Close the sort Fab */
    private void closeSortFab() {
        Timber.d("Close the fab");

        final Animator closePopularFab = AnimatorInflater.loadAnimator(getActivity(), R.animator.close_popular_fab);
        closePopularFab.setTarget(mFabPopular);
        closePopularFab.start();

        final Animator closeTopRatedFab = AnimatorInflater.loadAnimator(getActivity(), R.animator.close_toprated_fab);
        closeTopRatedFab.setTarget(mFabTopRated);
        closeTopRatedFab.start();

        final Animator openFavourite = AnimatorInflater.loadAnimator(getActivity(), R.animator.close_favourite_fab);
        openFavourite.setTarget(mFabFavourite);
        openFavourite.start();

        final Animator openSearch = AnimatorInflater.loadAnimator(getActivity(), R.animator.close_search_fab);
        openSearch.setTarget(mFabSearch);
        openSearch.start();

        mIsSortFabOpen = false;
    }

    /* Open the sort Fab */
    private void openSortFab() {
        Timber.d("Close the fab");

        final Animator openPopularFab = AnimatorInflater.loadAnimator(getActivity(), R.animator.open_popular_fab);
        openPopularFab.setTarget(mFabPopular);
        openPopularFab.start();

        final Animator openTopRatedTab = AnimatorInflater.loadAnimator(getActivity(), R.animator.open_toprated_fab);
        openTopRatedTab.setTarget(mFabTopRated);
        openTopRatedTab.start();

        final Animator openFavourite = AnimatorInflater.loadAnimator(getActivity(), R.animator.open_favourite_fab);
        openFavourite.setTarget(mFabFavourite);
        openFavourite.start();

        final Animator openSearch = AnimatorInflater.loadAnimator(getActivity(), R.animator.open_search_fab);
        openSearch.setTarget(mFabSearch);
        openSearch.start();

        mIsSortFabOpen = true;
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.fabSort)
    public void openSort() {

        if(mIsSortFabOpen) {
            closeSortFab();
        }
        else {
            openSortFab();
        }
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.fabPopular)
    public void getPopular() {
        Timber.d("getPopular");
        getPopularMovies();
        closeSortFab();
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.fabTopRated)
    public void getTopRated() {
        Timber.d("getTopRated");
        getTopRatedMovies();
        closeSortFab();
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.fabFavourite)
    public void getFavourites() {
        Timber.d("getFavourites");
        getFavouriteMovies();
        closeSortFab();
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.fabSearch)
    public void searchMovie() {
        Timber.d("searchMovie");

        final FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        final MovieSearchDialog movieSearchDialog = MovieSearchDialog.newInstance();
        movieSearchDialog.setTargetFragment(MovieListViewImp.this, 0);
        movieSearchDialog.show(fragmentManager, "MovieSearchDialog");

        closeSortFab();
    }

    private void setupSwipeToRefresh() {
        swipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_orange_light,
                android.R.color.holo_green_light,
                android.R.color.holo_red_light);

        swipeRefreshLayout.setOnRefreshListener(() -> mMovieListPresenterImp.getPopularMovies());
    }

    private void setupRecyclerView() {

        /* Portrait mode 2 columns as there is less width to display movies
           Landscape mode 3 columns as there is more width to display movies
         */
        final int columnCount = (getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_PORTRAIT) ? 2 : 3;

        final RecyclerView.LayoutManager gridLayoutManager
                = new GridLayoutManager(getActivity(), columnCount, LinearLayoutManager.VERTICAL, false);
        mRvMovieList.setLayoutManager(gridLayoutManager);
        mRvMovieList.setHasFixedSize(true);
        mMovieAdapter = new MovieAdapter(Collections.emptyList());
        mRvMovieList.setAdapter(mMovieAdapter);
    }

    public void getPopularMovies() {
        /* Display progress indicator */
        showProgressBar();
        mMovieListPresenterImp.getPopularMovies();
    }

    public void getTopRatedMovies() {
        /* Display progress indicator */
        showProgressBar();
        mMovieListPresenterImp.getTopRatedMovies();
    }

    public void getFavouriteMovies() {
        showProgressBar();
        mMovieFavouritePresenterImp.getFavouriteMovies(MovieListViewImp.this);
    }
    ///
    @Override
    public void displayPopularMovies(Results<Movies> popularMovies) {
        /* Load adapter with data to be populated in the recycler view */
        /* Hide progress indicator */
        Timber.d("displayPopularMovies Received %d", popularMovies.getResults().size());

        hideProgressBar();

        mMovieAdapter.loadAdapter(popularMovies);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void displayTopRatedMovies(Results<Movies> topRatedMovies) {
        Timber.d("displayTopRatedMovies: %d", topRatedMovies.getResults().size());

        hideProgressBar();

        mMovieAdapter.loadAdapter(topRatedMovies);
    }

    @Override
    public void onGetFavouriteMoviesSuccess(Results<Movie> favouriteList) {
        Timber.d("onGetFavouriteMovieSuccess %d", favouriteList.getResults().size());

        hideProgressBar();

        if(favouriteList.getResults().size() > 0) {
            mMovieAdapter.loadAdapter(favouriteList);
        }
        else {
            Toast.makeText(getActivity(), "There are no favourites to display", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    @Override
    public void failedToDisplayPopularMovies(String errorMessage) {
        Toast.makeText(getActivity(), "Failed to get popular movies\n" + errorMessage, Toast.LENGTH_LONG).show();
        mPbMovieList.hide();
        swipeRefreshLayout.setRefreshing(false);

        Timber.w("Failed to get popular movies %s", errorMessage);
    }

    @Override
    public void failedToDisplayTopRatedMovies(String errorMessage) {
        Toast.makeText(getActivity(), "Failed to get top rated movies\n" + errorMessage, Toast.LENGTH_LONG).show();
        mPbMovieList.hide();
        Timber.d("Failed to get top rated movies %s", errorMessage);
    }

/*
    @Override
    public void displayFavouriteMovies(List<Favourite> favouriteList) {
        Timber.d("displayTopRatedMovies: %d", favouriteList.size());

        hideProgressBar();

      //  mMovieAdapter.loadAdapter(favouriteList);
    }

    @Override
    public void failedDisplayFavouriteMovies(String errorMessage) {
        Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_LONG).show();
    }

    @Override
    public void failedFavouriteMovieDelete(String errorMessage) {
        Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_LONG).show();
    }

    @Override
    public void successFavouriteMovieDelete() {
        Timber.d("Movie deleted from favourites");
        Toast.makeText(getActivity(), "Movie favourite movie deleted", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void failedFavouriteMovieInsert(String errorMessage) {
        Timber.e("failedFavouriteMovieInsert %s", errorMessage);
        Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_LONG).show();
    }

    @Override
    public void successFavouriteMovieInsert() {
        Timber.d("successFavouriteMovieInsert");
        Toast.makeText(getActivity(), "Insert favourite movie", Toast.LENGTH_SHORT).show();
    }
*/

    @Override
    public void onMovieSearch(@NotNull String movieName, int movieYear) {
        showProgressBar();
        mMovieListPresenterImp.searchMovies(movieName, movieYear);
    }

    @Override
    public void failedToGetSearchMovies(String errorMessage) {
        Timber.e("failedToGetSearchMovies: %s", errorMessage);
        hideProgressBar();
    }

    @Override
    public void successToGetSearchMovies(Results<Movies> movieSearch) {
        Timber.d("successToGetSearchMovies: %d", movieSearch.getResults().size());
        hideProgressBar();
        mMovieAdapter.loadAdapter(movieSearch);
    }

    private void hideProgressBar() {
        if(mPbMovieList.isShown()) {
            mPbMovieList.hide();
        }
    }

    private void showProgressBar() {
        if(!mPbMovieList.isShown()) {
            mPbMovieList.show();
        }
    }





    @Override
    public void onGetFavouriteMoviesFailure(String errorMessage) {

    }

    @Override
    public void onInsertFavouriteSuccess() {
        Timber.d("onInsertFavouriteSuccess");
    }

    @Override
    public void onInsertFavouriteFailure(String errorMessage) {
    }

    @Override
    public void onDeleteFavouriteMovieSuccess(int rowDeletedId) {
    }

    @Override
    public void onDeleteFavouriteMovieFailure(String errorMessage) {
    }

    @Override
    public void onHasMovieFavouriteSuccess(int movieId, boolean isFavourite) {

    }

    @Override
    public void onHasMovieFavouriteFailure(String errorMessage) {

    }

    @Override
    public void onGetMovieFavouriteSuccess(Movie favourite) {

    }

    @Override
    public void onGetMovieFavouriteFailure(String errorMessage) {

    }
}
