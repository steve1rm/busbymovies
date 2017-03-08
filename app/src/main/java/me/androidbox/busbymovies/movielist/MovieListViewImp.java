package me.androidbox.busbymovies.movielist;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
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

import static android.animation.AnimatorInflater.loadAnimator;

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

        if(mPbMovieList.isShown()) {
            mPbMovieList.hide();
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
        mUnbinder.unbind();
        mMovieListPresenterImp.detachView();
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.fabSort)
    public void openSort() {
/*        final Animator animator = AnimatorInflater.loadAnimator(getActivity(), R.animator.open_fabs);
        animator.setTarget(mFabPopular);
        animator.start();*/

        if(mFabPopular.getVisibility() == View.INVISIBLE && mFabTopRated.getVisibility() == View.INVISIBLE) {
            Timber.d("BEFORE mFabPopular.getTop(): %d", mFabPopular.getTop());
            final ObjectAnimator moveFabPopular
                    = ObjectAnimator.ofFloat(mFabPopular, View.TRANSLATION_Y, mFabPopular.getTop(), -100);


            final ObjectAnimator moveFabTopRated = ObjectAnimator.ofFloat(mFabTopRated, View.TRANSLATION_Y, mFabPopular.getY(), -100);

            mFabPopular.setVisibility(View.VISIBLE);
            moveFabPopular.setDuration(300);
            moveFabPopular.setInterpolator(new DecelerateInterpolator());
            moveFabPopular.start();

     //       mFabTopRated.setVisibility(View.VISIBLE);
            moveFabTopRated.setDuration(300);
            moveFabTopRated.setInterpolator(new DecelerateInterpolator());
     //       moveFabTopRated.start();
        }
        else {
            final ObjectAnimator moveFabPopular
                    = ObjectAnimator.ofFloat(mFabPopular, View.Y, 100);

            moveFabPopular.setDuration(300);
            moveFabPopular.setInterpolator(new DecelerateInterpolator());
            moveFabPopular.start();

            final ObjectAnimator moveFabTopRated = ObjectAnimator.ofFloat(mFabTopRated, View.TRANSLATION_Y, 500);
            moveFabTopRated.setDuration(300);
            moveFabTopRated.setInterpolator(new DecelerateInterpolator());

            moveFabPopular.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    mFabPopular.setVisibility(View.INVISIBLE);

                    moveFabTopRated.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            mFabTopRated.setVisibility(View.INVISIBLE);
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    });
                }
            });

//            moveFabTopRated.start();
            Timber.d("AFTER mFabPopular.getY(): %f", mFabPopular.getY());
            Timber.d("reverse animation");
        }
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
