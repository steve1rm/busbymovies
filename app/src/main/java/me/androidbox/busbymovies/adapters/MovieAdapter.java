package me.androidbox.busbymovies.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import me.androidbox.busbymovies.R;
import me.androidbox.busbymovies.models.Movies;
import me.androidbox.busbymovies.models.Results;
import me.androidbox.busbymovies.movielist.MovieListViewHolder;

/**
 * Created by steve on 2/27/17.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieListViewHolder> {
    /* Make this more generic */
    private List<? extends Movies> mMovieList = Collections.emptyList();

    public MovieAdapter(final List<? extends Movies> movieList) {
        this.mMovieList = new ArrayList<>(movieList);
    }

    @Override
    public MovieListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        final View view = layoutInflater.inflate(R.layout.movielist_item, parent, false);

        return new MovieListViewHolder(view, MovieAdapter.this, parent.getContext());
    }

    @Override
    public void onBindViewHolder(MovieListViewHolder holder, int position) {
        holder.bindViewData(mMovieList.get(position).getTitle(), mMovieList.get(position).getPoster_path());
    }

    public void loadAdapter(Results<? extends Movies> results) {
        clearAllMovies();
        mMovieList = results.getResults();
        notifyItemRangeInserted(0, mMovieList.size());
    }

    public void clearAllMovies() {
        int count = getItemCount();
        mMovieList.clear();
        notifyItemRangeRemoved(0, count);
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
