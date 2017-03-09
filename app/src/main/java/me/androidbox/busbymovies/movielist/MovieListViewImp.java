package me.androidbox.busbymovies.movielist;

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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import java.util.Collections;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import me.androidbox.busbymovies.R;
import me.androidbox.busbymovies.adapters.MovieAdapter;
import me.androidbox.busbymovies.di.DaggerInjector;
import me.androidbox.busbymovies.models.Results;
import timber.log.Timber;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieListViewImp extends Fragment implements MovieListViewContract {
    public static final String TAG = MovieListViewImp.class.getSimpleName();

    @Inject MovieListPresenterContract<MovieListViewContract> mMovieListPresenterImp;

    @BindView(R.id.tool_bar) Toolbar mToolbar;
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

        setupToolbar();
        setupRecyclerView();

        /* Hide the progress bar */
        if(mPbMovieList.isShown()) {
            mPbMovieList.hide();
        }

        if(mIsSortFabOpen) {
            mIsSortFabOpen = false;
/*
            mFabPopular.setVisibility(View.INVISIBLE);
            mFabTopRated.setVisibility(View.INVISIBLE);
*/
        }

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        DaggerInjector.getApplicationComponent().inject(MovieListViewImp.this);
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

    @SuppressWarnings("unused")
    @OnClick(R.id.fabSort)
    public void openSort() {

        if(mIsSortFabOpen) {
            Timber.d("Close the fab");
            final Animation closePopularFab = AnimationUtils.loadAnimation(getActivity(), R.anim.close_popular_fab);
            final Animation closeTopRatedFab = AnimationUtils.loadAnimation(getActivity(), R.anim.close_toprated_fab);

            mFabPopular.startAnimation(closePopularFab);
            mFabTopRated.startAnimation(closeTopRatedFab);

            mIsSortFabOpen = false;
        }
        else {
            Timber.d("Open the fab");
            final Animation openPopularFab = AnimationUtils.loadAnimation(getActivity(), R.anim.open_popular_fab);
            final Animation openTopRatedFab = AnimationUtils.loadAnimation(getActivity(), R.anim.open_toprated_fab);

            mFabPopular.setVisibility(View.VISIBLE);
            mFabPopular.startAnimation(openPopularFab);
            mFabTopRated.startAnimation(openTopRatedFab);

            mIsSortFabOpen = true;
        }
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.fabPopular)
    public void getPopular() {
        Timber.d("getPopular");
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.fabTopRated)
    public void getTopRated() {
        Timber.d("getTopRated");
    }

    /**
     * Setup toolbar
     */
    private void setupToolbar() {
        AppCompatActivity appCompatActivity = (AppCompatActivity)getActivity();
        appCompatActivity.setSupportActionBar(mToolbar);
    }

    private void setupRecyclerView() {
        final RecyclerView.LayoutManager gridLayoutManager
                = new GridLayoutManager(getActivity(), 2, LinearLayoutManager.VERTICAL, false);
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

    }

    @Override
    public void displayPopularMovies(Results popularMovies) {
        /* Load adapter with data to be populated in the recycler view */
        /* Hide progress indicator */
        Timber.d("Received %d", popularMovies.getResults().size());

        if(mPbMovieList.isShown()) {
            mPbMovieList.hide();
        }

        mMovieAdapter.loadAdapter(popularMovies);
    }

    @Override
    public void displayTopRatedMovies(String movies) {

    }

    @Override
    public void failedToDisplayPopularMovies(String errorMessage) {
        Toast.makeText(getActivity(), "Failed to get popular movies", Toast.LENGTH_LONG).show();
        Timber.w("Failed to get popular movies %s", errorMessage);
    }

    @Override
    public void failedToDisplayTopRatedMovies(String errorMessage) {
        Timber.d("Failed to get top rated movies %s", errorMessage);
    }
}
