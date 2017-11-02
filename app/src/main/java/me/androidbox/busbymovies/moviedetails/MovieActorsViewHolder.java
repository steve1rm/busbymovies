package me.androidbox.busbymovies.moviedetails;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Priority;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.androidbox.busbymovies.R;
import me.androidbox.busbymovies.di.BusbyMoviesMainApplication;
import me.androidbox.busbymovies.models.Actor;
import me.androidbox.busbymovies.utils.ImageLoader;
import me.androidbox.busbymovies.utils.MovieImage;

/**
 * Created by steve on 9/16/17.
 */

public class MovieActorsViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.civActorPicture) ImageView actorPicture;
    @BindView(R.id.tvName) TextView name;
    @BindView(R.id.tvCharacter) TextView character;

    private final Context context;
    private final ImageLoader imageLoader;

    public MovieActorsViewHolder(final View itemView, final ImageLoader imageLoader) {
        super(itemView);

        ButterKnife.bind(this, itemView);

        this.imageLoader = imageLoader;
        this.context = itemView.getContext();
    }

    public void populateActor(Actor actor) {
        imageLoader.load(
                context,
                MovieImage.build(actor.getProfile_path(), MovieImage.ImageSize.w92),
                R.drawable.placeholder_poster,
                actorPicture,
                Priority.HIGH);

        name.setText(actor.getName());
        character.setText(actor.getCharacter());
    }
}
