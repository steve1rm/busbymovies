package me.androidbox.busbymovies.movielist;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.androidbox.busbymovies.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieListView extends Fragment {
    public static final String TAG = MovieListView.class.getSimpleName();

    public MovieListView() {
        // Required empty public constructor
    }

    /* Factory method */
    public static MovieListView newInstance() {
        return new MovieListView();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.movie_list_view, container, false);
    }

}
