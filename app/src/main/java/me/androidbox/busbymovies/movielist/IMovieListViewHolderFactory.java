package me.androidbox.busbymovies.movielist;

import android.view.View;

import me.androidbox.busbymovies.adapters.MovieAdapter;
import me.androidbox.busbymovies.utils.ImageLoader;

public interface IMovieListViewHolderFactory {
    MovieListViewHolder createViewHolder(final View parent,
                                         final MovieAdapter movieAdapter,
                                         final ImageLoader imageLoader,
                                         final MovieListItemClickedListener movieListItemClickedListener);
}
