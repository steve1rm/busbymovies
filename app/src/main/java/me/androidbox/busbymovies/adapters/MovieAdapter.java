package me.androidbox.busbymovies.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import me.androidbox.busbymovies.R;
import me.androidbox.busbymovies.models.Movie;
import me.androidbox.busbymovies.models.Results;
import me.androidbox.busbymovies.movielist.MovieListViewHolder;
import me.androidbox.busbymovies.movielist.MovieSelectedListener;

/**
 * Created by steve on 2/27/17.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieListViewHolder> {

    private List<Movie> mMovieList = Collections.emptyList();

    public MovieAdapter(List<Movie> movieList) {
        mMovieList = new ArrayList<>(movieList);
    }

    @Override
    public MovieListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        final View view = layoutInflater.inflate(R.layout.movielist_item, parent, false);

        return new MovieListViewHolder(view, MovieAdapter.this);
    }

    @Override
    public void onBindViewHolder(MovieListViewHolder holder, int position) {
        holder.mTvTagLine.setText(mMovieList.get(position).getTitle());
    }

    public void loadAdapter(Results results) {
        mMovieList.addAll(results.getResults());
        notifyItemRangeInserted(0, results.getResults().size());
    }

    @Override
    public int getItemCount() {
        if(mMovieList != null) {
            return mMovieList.size();
        }
        else {
            return Collections.emptyList().size();
        }
    }

    public int getMovieId(int moviePosition) {
        return mMovieList.get(moviePosition).getId();
    }
}
