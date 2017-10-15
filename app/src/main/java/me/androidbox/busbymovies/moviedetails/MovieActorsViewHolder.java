package me.androidbox.busbymovies.moviedetails;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Priority;
import com.google.auto.factory.AutoFactory;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.androidbox.busbymovies.R;
import me.androidbox.busbymovies.di.DaggerInjector;
import me.androidbox.busbymovies.models.Actor;
import me.androidbox.busbymovies.utils.GlideApp;
import me.androidbox.busbymovies.utils.MovieImage;

/**
 * Created by steve on 9/16/17.
 */

@AutoFactory
public class MovieActorsViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.civActorPicture) ImageView actorPicture;
    @BindView(R.id.tvName) TextView name;
    @BindView(R.id.tvCharacter) TextView character;

    @Inject ImageLoader imageLoader;
    private Context context;

    public MovieActorsViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

        DaggerInjector.getApplicationComponent().inject(MovieActorsViewHolder.this);

        context = itemView.getContext();
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
