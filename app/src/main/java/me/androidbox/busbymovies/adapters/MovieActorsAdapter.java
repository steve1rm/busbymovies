package me.androidbox.busbymovies.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import me.androidbox.busbymovies.R;
import me.androidbox.busbymovies.models.Actor;
import me.androidbox.busbymovies.models.Cast;
import me.androidbox.busbymovies.moviedetails.MovieActorsViewHolder;
import me.androidbox.busbymovies.utils.ImageLoader;

/**
 * Created by steve on 9/16/17.
 */

public class MovieActorsAdapter extends RecyclerView.Adapter<MovieActorsViewHolder> {
    private final List<Actor> actorList;
    private final ImageLoader imageLoader;

    public MovieActorsAdapter(final ImageLoader imageLoader) {
        this.actorList = new ArrayList<>();
        this.imageLoader = imageLoader;
    }

    @Override
    public MovieActorsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_actors_item, parent, false);

        return new MovieActorsViewHolder(view, imageLoader);
    }

    @Override
    public void onBindViewHolder(MovieActorsViewHolder holder, int position) {
        holder.populateActor(actorList.get(position));
    }

    @Override
    public int getItemCount() {
        return actorList.size();
    }

    public void populateActors(Cast<Actor> actorList) {
        this.actorList.clear();
        notifyDataSetChanged();
        this.actorList.addAll(actorList.getCast());

        notifyItemRangeInserted(0, this.actorList.size());
    }
}
