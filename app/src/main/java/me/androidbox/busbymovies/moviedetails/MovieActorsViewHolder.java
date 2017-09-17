package me.androidbox.busbymovies.moviedetails;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.auto.factory.AutoFactory;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import me.androidbox.busbymovies.R;
import me.androidbox.busbymovies.models.Actor;
import me.androidbox.busbymovies.utils.MovieImage;

/**
 * Created by steve on 9/16/17.
 */
@AutoFactory
public class MovieActorsViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.civActorPicture) CircleImageView actorPicture;
    @BindView(R.id.tvName) TextView name;
    @BindView(R.id.tvCharacter) TextView character;

    private Context context;

    public MovieActorsViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

        context = itemView.getContext();
    }

    public void populateActor(Actor actor) {
        Glide.with(context)
                .load(MovieImage.build(actor.getProfile_path(), MovieImage.ImageSize.w92))
                .placeholder(R.drawable.people_placeholder)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(actorPicture);

        name.setText(actor.getName());
        character.setText(actor.getCharacter());
    }
}
