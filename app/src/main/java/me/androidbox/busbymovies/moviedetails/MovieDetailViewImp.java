package me.androidbox.busbymovies.moviedetails;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.androidbox.busbymovies.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class MovieDetailViewImp extends Fragment {
    public static final String TAG = MovieDetailViewImp.class.getSimpleName();
    public static final String MOVIE_ID_KEY = "movie_id_key";

    public MovieDetailViewImp() {
        // Required empty public constructor
    }

    /* Factory Method */
    public static MovieDetailViewImp newInstance(int movieId) {
        final Bundle args = new Bundle();
        args.putInt(MOVIE_ID_KEY, movieId);

        MovieDetailViewImp movieDetailViewImp = new MovieDetailViewImp();
        movieDetailViewImp.setArguments(args);

        return movieDetailViewImp;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.movie_detail_view, container, false);
    }

}
