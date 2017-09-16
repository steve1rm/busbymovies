package me.androidbox.busbymovies.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import me.androidbox.busbymovies.R;
import me.androidbox.busbymovies.models.Actor;
import me.androidbox.busbymovies.models.Cast;
import me.androidbox.busbymovies.moviedetails.MovieActorsViewHolder;

/**
 * Created by steve on 9/16/17.
 */

public class MovieActorsAdapter extends RecyclerView.Adapter<MovieActorsViewHolder> {
    private List<Actor> actorList = Collections.emptyList();

    public MovieActorsAdapter() {
        this.actorList = new ArrayList<>();
    }

    @Override
    public MovieActorsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_actors_item, parent, false);

        return new MovieActorsViewHolder(view);
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
        this.actorList.addAll(actorList.getCast());
        notifyItemRangeInserted(0, actorList.getCast().size());
    }
}
