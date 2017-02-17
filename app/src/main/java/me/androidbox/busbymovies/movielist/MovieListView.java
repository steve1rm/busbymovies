package me.androidbox.busbymovies.movielist;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.androidbox.busbymovies.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieListView extends Fragment {
    public static final String TAG = MovieListView.class.getSimpleName();

    @BindView(R.id.tool_bar) Toolbar mToolbar;

    private Unbinder mUnbinder;

    public MovieListView() {
        // Required empty public constructor
    }

    /* Factory method */
    public static MovieListView newInstance() {
        return new MovieListView();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.movie_list_view, container, false);

        mUnbinder = ButterKnife.bind(MovieListView.this, view);

        setupToolbar();

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    /**
     * Setup toolbar
     */
    private void setupToolbar() {
        AppCompatActivity appCompatActivity = (AppCompatActivity)getActivity();
        appCompatActivity.setSupportActionBar(mToolbar);
    }
}
