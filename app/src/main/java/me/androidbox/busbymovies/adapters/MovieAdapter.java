package me.androidbox.busbymovies.adapters;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import me.androidbox.busbymovies.R;
import me.androidbox.busbymovies.models.Movies;
import me.androidbox.busbymovies.models.Results;
import me.androidbox.busbymovies.movielist.IMovieListViewHolderFactory;
import me.androidbox.busbymovies.movielist.MovieListItemClickedListener;
import me.androidbox.busbymovies.movielist.MovieListViewHolder;
import me.androidbox.busbymovies.utils.Constants;
import me.androidbox.busbymovies.utils.ImageLoader;

/**
 * Created by steve on 2/27/17.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieListViewHolder> {
    /* Make this more generic */
    private List<? extends Movies> mMovieList;
    private ImageLoader imageLoader;
    private Map<Integer, IMovieListViewHolderFactory> viewHolderFactories;
    private MovieListItemClickedListener movieListItemClickedListener;

    @Inject
    public MovieAdapter(final ImageLoader imageLoader, final Map<Integer, IMovieListViewHolderFactory> viewHolderFactories, final MovieListItemClickedListener movieListItemClickedListener) {
        this.mMovieList = new ArrayList<>(Collections.emptyList());
        this.imageLoader = imageLoader;
        this.viewHolderFactories = viewHolderFactories;
        this.movieListItemClickedListener = movieListItemClickedListener;
    }

    @Override
    public MovieListViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        final View view = layoutInflater.inflate(R.layout.movielist_item, parent, false);

        return viewHolderFactories.get(Constants.PORTRAIT).createViewHolder(
                view,
                MovieAdapter.this,
                imageLoader,
                movieListItemClickedListener);
    }

    @Override
    public void onBindViewHolder(final MovieListViewHolder holder, final int position) {
        holder.bindViewData(mMovieList.get(position).getTitle(), mMovieList.get(position).getPoster_path());
    }

    public void loadAdapter(final Results<? extends Movies> results) {
        clearAllMovies();
        mMovieList = results.getResults();
        notifyItemRangeInserted(0, mMovieList.size());
    }

    private void clearAllMovies() {
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

    public int getMovieId(final int moviePosition) {
        return mMovieList.get(moviePosition).getId();
    }
}
