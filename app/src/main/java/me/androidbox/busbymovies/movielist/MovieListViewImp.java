package me.androidbox.busbymovies.movielist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

    private Unbinder mUnbinder;
    private MovieAdapter mMovieAdapter;

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
        mUnbinder.unbind();
        mMovieListPresenterImp.detachView();
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

        mMovieAdapter = new MovieAdapter(Collections.emptyList());
        mRvMovieList.setAdapter(mMovieAdapter);
    }

    public void getPopularMovies() {
        /* Display progress indicator */
        mMovieListPresenterImp.getPopularMovies();
    }

    public void getTopRatedMovies() {

    }

    @Override
    public void displayPopularMovies(Results popularMovies) {
        /* Load adapter with data to be populated in the recycler view */
        /* Hide progress indicator */
        Timber.d("Received %d", popularMovies.getResults().size());

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
