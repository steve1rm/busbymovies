package me.androidbox.busbymovies.movielist;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.Collections;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import me.androidbox.busbymovies.R;
import me.androidbox.busbymovies.adapters.MovieAdapter;
import me.androidbox.busbymovies.data.MovieFavouritesPresenterContract;
import me.androidbox.busbymovies.di.DaggerInjector;
import me.androidbox.busbymovies.models.Results;
import timber.log.Timber;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieListViewImp extends Fragment implements MovieListViewContract {
    public static final String TAG = MovieListViewImp.class.getSimpleName();

    @Inject MovieListPresenterContract<MovieListViewContract> mMovieListPresenterImp;
    @Inject MovieFavouritesPresenterContract<MovieListViewContract> mMovieFavouritePresenterImp;

    @Nullable @BindView(R.id.tool_bar) Toolbar mToolbar;
    @BindView(R.id.rvMovieList) RecyclerView mRvMovieList;
    @BindView(R.id.pbMovieList) ContentLoadingProgressBar mPbMovieList;
    @BindView(R.id.fabPopular) FloatingActionButton mFabPopular;
    @BindView(R.id.fabTopRated) FloatingActionButton mFabTopRated;

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

        /* Don't display the toolbar if when in landscape mode to create more real-estate */
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            setupToolbar();
        }

        setupRecyclerView();

        /* Hide the progress bar */
        if(mPbMovieList.isShown()) {
            mPbMovieList.hide();
        }

        if(mIsSortFabOpen) {
            mIsSortFabOpen = false;
        }

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        DaggerInjector.getApplicationComponent().inject(MovieListViewImp.this);

        if(mMovieFavouritePresenterImp != null) {
            Timber.d("mMovieFavouritePresenterImp != null");
            mMovieFavouritePresenterImp.attachView(MovieListViewImp.this);
            mMovieFavouritePresenterImp.getFavouriteMovies();
        }

        if(mMovieListPresenterImp != null) {
            Timber.d("mMovieListPresenterImp != null");
            mMovieListPresenterImp.attachView(MovieListViewImp.this);
            getPopularMovies();
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

    /**
     * Setup toolbar
     */
    @SuppressWarnings("all")
    private void setupToolbar() {
        AppCompatActivity appCompatActivity = (AppCompatActivity)getActivity();
        appCompatActivity.setSupportActionBar(mToolbar);
        appCompatActivity.getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        appCompatActivity.getSupportActionBar().setDisplayUseLogoEnabled(true);
        appCompatActivity.getSupportActionBar().setDisplayShowTitleEnabled(true);
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
        if(!mPbMovieList.isShown()) {
            mPbMovieList.show();
        }

        mMovieListPresenterImp.getPopularMovies();
    }

    public void getTopRatedMovies() {
        /* Display progress indicator */
        if(!mPbMovieList.isShown()) {
            mPbMovieList.show();
        }

        mMovieListPresenterImp.getTopRatedMovies();
    }

    @Override
    public void displayPopularMovies(Results popularMovies) {
        /* Load adapter with data to be populated in the recycler view */
        /* Hide progress indicator */
        Timber.d("displayPopularMovies Received %d", popularMovies.getResults().size());

        if(mPbMovieList.isShown()) {
            mPbMovieList.hide();
        }

        mMovieAdapter.loadAdapter(popularMovies);
    }

    @Override
    public void displayTopRatedMovies(Results topRatedMovies) {
        Timber.d("displayTopRatedMovies: %d", topRatedMovies.getResults().size());

        if(mPbMovieList.isShown()) {
            mPbMovieList.hide();
        }

        mMovieAdapter.loadAdapter(topRatedMovies);
    }

    @Override
    public void failedToDisplayPopularMovies(String errorMessage) {
        Toast.makeText(getActivity(), "Failed to get popular movies\n" + errorMessage, Toast.LENGTH_LONG).show();
        mPbMovieList.hide();
        Timber.w("Failed to get popular movies %s", errorMessage);
    }

    @Override
    public void failedToDisplayTopRatedMovies(String errorMessage) {
        Toast.makeText(getActivity(), "Failed to get top rated movies\n" + errorMessage, Toast.LENGTH_LONG).show();
        mPbMovieList.hide();
        Timber.d("Failed to get top rated movies %s", errorMessage);
    }
}
