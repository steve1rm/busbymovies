package me.androidbox.busbymovies.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

import me.androidbox.busbymovies.R;
import me.androidbox.busbymovies.models.Trailer;
import me.androidbox.busbymovies.moviedetails.MovieTrailerListener;
import me.androidbox.busbymovies.moviedetails.MovieTrailerViewHolder;

/**
 * Created by steve on 3/31/17.
 */

public class MovieTrailerAdapter extends RecyclerView.Adapter<MovieTrailerViewHolder> {

    private List<Trailer> mTrailerList = Collections.emptyList();
    private MovieTrailerListener mMovieTrailerListener;
    private MovieTrailerViewHolder mMovieTrailerViewHolder;

    public MovieTrailerAdapter(List<Trailer> trailerList, MovieTrailerListener movieTrailerListener) {
        this.mTrailerList = trailerList;
        mMovieTrailerListener = movieTrailerListener;
    }

    @Override
    public MovieTrailerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View view = inflater.inflate(R.layout.trailer_item, parent, false);

        mMovieTrailerViewHolder = new MovieTrailerViewHolder(view, mMovieTrailerListener);

        return mMovieTrailerViewHolder;
    }

    @Override
    public void onBindViewHolder(MovieTrailerViewHolder holder, int position) {
        Trailer trailer = mTrailerList.get(position);
        mMovieTrailerViewHolder.setViewData(trailer);
    }

    @Override
    public int getItemCount() {
        return mTrailerList.size();
    }
}
