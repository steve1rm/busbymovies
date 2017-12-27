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
public class MovieListViewImp
        extends
        Fragment
        implements
        MovieListViewContract,
        MovieFavouritePresenterContract.MovieFavouriteListListener,
        MovieSearchListener {
    public static final String TAG = MovieListViewImp.class.getSimpleName();

    @Inject MovieListPresenterContract mMovieListPresenterImp;
    @Inject MovieFavouritePresenterContract mMovieFavouritePresenterImp;

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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((BusbyMoviesMainApplication)getActivity().getApplication())
                .getMovieListComponent(MovieListViewImp.this)
                .inject(this);

        mMovieListPresenterImp.attachView(MovieListViewImp.this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.movie_list_view, container, false);

        mUnbinder = ButterKnife.bind(MovieListViewImp.this, view);
        setupRecyclerView();
        setupSwipeToRefresh();
        mMovieListPresenterImp.getPopularMovies();

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Timber.d("onDestroyView");

        /* close the fab button if open */
        if(mIsSortFabOpen) {
            mIsSortFabOpen = false;
        }

        mMovieListPresenterImp.detachView();
        mUnbinder.unbind();
    }

    /* Close the sort Fab */
    @Override
    public void onCloseSortFab() {
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
    @Override
    public void onOpenSortFab() {
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
            mMovieListPresenterImp.closeSortFab();
        }
        else {
            mMovieListPresenterImp.openSortFab();
        }
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.fabPopular)
    public void getPopular() {
        Timber.d("getPopular");
        mMovieListPresenterImp.getPopularMovies();
        mMovieListPresenterImp.closeSortFab();
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.fabTopRated)
    public void getTopRated() {
        Timber.d("getTopRated");
        mMovieListPresenterImp.getTopRatedMovies();
        mMovieListPresenterImp.closeSortFab();
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.fabFavourite)
    public void getFavourites() {
        Timber.d("getFavourites");
        mMovieFavouritePresenterImp.getFavouriteMovies(MovieListViewImp.this);
        mMovieListPresenterImp.closeSortFab();
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.fabSearch)
    public void searchMovie() {
        Timber.d("searchMovie");

        final FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        final MovieSearchDialog movieSearchDialog = MovieSearchDialog.newInstance();
        movieSearchDialog.setTargetFragment(MovieListViewImp.this, 0);
        movieSearchDialog.show(fragmentManager, "MovieSearchDialog");

        mMovieListPresenterImp.closeSortFab();
    }

    private void setupSwipeToRefresh() {
        swipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_orange_light,
                android.R.color.holo_green_light,
                android.R.color.holo_red_light);
        /* TODO Swipe to refresh should fetch movies related to what is on the search i.e. popular, top rated, searched, or favourites */
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

    @Override
    public void displayPopularMovies(Results<Movies> popularMovies) {
        /* Load adapter with data to be populated in the recycler view */
        /* Hide progress indicator */
        Timber.d("displayPopularMovies Received %d", popularMovies.getResults().size());
        mMovieAdapter.loadAdapter(popularMovies);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void displayTopRatedMovies(Results<Movies> topRatedMovies) {
        Timber.d("displayTopRatedMovies: %d", topRatedMovies.getResults().size());
        mMovieAdapter.loadAdapter(topRatedMovies);
    }

    @Override
    public void onGetFavouriteMoviesSuccess(Results<Movie> favouriteList) {
        Timber.d("onGetFavouriteMovieSuccess %d", favouriteList.getResults().size());

        if(favouriteList.getResults().size() > 0) {
            mMovieAdapter.loadAdapter(favouriteList);
        }
        else {
            Toast.makeText(getActivity(), "There are no favourites to display", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    @Override
    public void onGetFavouriteMoviesFailure(String errorMessage) {
        Timber.e("onGetFavouriteMoviesFailure: %s", errorMessage);
        Toast.makeText(getActivity(), "Failed to get favourite movies", Toast.LENGTH_LONG).show();
    }

    @Override
    public void failedToDisplayPopularMovies(String errorMessage) {
        Toast.makeText(getActivity(), "Failed to get popular movies\n" + errorMessage, Toast.LENGTH_LONG).show();
        swipeRefreshLayout.setRefreshing(false);

        Timber.w("Failed to get popular movies %s", errorMessage);
    }

    @Override
    public void failedToDisplayTopRatedMovies(String errorMessage) {
        Toast.makeText(getActivity(), "Failed to get top rated movies\n" + errorMessage, Toast.LENGTH_LONG).show();
        mPbMovieList.hide();
        Timber.d("Failed to get top rated movies %s", errorMessage);
    }

    @Override
    public void onMovieSearch(@NotNull String movieName, int movieYear) {
        mMovieListPresenterImp.searchMovies(movieName, movieYear);
    }

    @Override
    public void failedToGetSearchMovies(String errorMessage) {
        Timber.w("failedToGetSearchMovies: %s", errorMessage);
        Toast.makeText(getActivity(), "Failed to find any movies", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void successToGetSearchMovies(Results<Movies> movieSearch) {
        mMovieAdapter.loadAdapter(movieSearch);
    }

    @Override
    public void onHideProgressBar() {
        if(mPbMovieList.isShown()) {
            mPbMovieList.hide();
        }
    }

    @Override
    public void onShowProgressBar() {
        if(!mPbMovieList.isShown()) {
            mPbMovieList.show();
        }
    }
}
