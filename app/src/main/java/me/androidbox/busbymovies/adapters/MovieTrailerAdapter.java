package me.androidbox.busbymovies.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.androidbox.busbymovies.R;
import me.androidbox.busbymovies.models.Results;
import me.androidbox.busbymovies.models.Trailer;
import me.androidbox.busbymovies.moviedetails.MovieTrailerViewHolder;
import me.androidbox.busbymovies.moviedetails.StartMovieTrailerListener;

/**
 * Created by steve on 3/31/17.
 */

public class MovieTrailerAdapter extends RecyclerView.Adapter<MovieTrailerViewHolder> {

    private Results<Trailer> mTrailerList;
    private StartMovieTrailerListener mMovieTrailerListener;
    private MovieTrailerViewHolder mMovieTrailerViewHolder;

    public MovieTrailerAdapter(Results<Trailer> trailerList, StartMovieTrailerListener movieTrailerListener) {
        mTrailerList = trailerList;
        mMovieTrailerListener = movieTrailerListener;
    }

    @Override
    public MovieTrailerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View view = inflater.inflate(R.layout.trailer_item, parent, false);

        mMovieTrailerViewHolder = new MovieTrailerViewHolder(view, MovieTrailerAdapter.this, mMovieTrailerListener, parent.getContext());
        return mMovieTrailerViewHolder;
    }

    @Override
    public void onBindViewHolder(MovieTrailerViewHolder holder, int position) {
        mMovieTrailerViewHolder.setViewData(mTrailerList.getResults().get(position));
    }

    @Override
    public int getItemCount() {
        return mTrailerList.getResults().size();
    }

    public Trailer getTrailerFromPosition(int position) {
        return mTrailerList.getResults().get(position);
    }
}
