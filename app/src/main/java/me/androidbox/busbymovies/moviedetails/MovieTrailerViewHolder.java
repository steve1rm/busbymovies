package me.androidbox.busbymovies.moviedetails;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeStandalonePlayer;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.androidbox.busbymovies.R;
import me.androidbox.busbymovies.adapters.MovieTrailerAdapter;
import me.androidbox.busbymovies.models.Trailer;
import me.androidbox.busbymovies.utils.Constants;
import me.androidbox.busbymovies.utils.GlideApp;
import me.androidbox.busbymovies.utils.MovieImage;
import timber.log.Timber;

/**
 * Created by steve on 3/31/17.
 */

public class MovieTrailerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    @BindView(R.id.youtubeThumbnail) ImageView mYoutubeThumbnail;
    @BindView(R.id.ivPlayTrailerItem) ImageView mIvPlayTrailerItem;

//    private StartMovieTrailerListener mMovieTrailerListener;
    private MovieTrailerAdapter mMovieTrailerAdapter;
    private WeakReference<Context> mContext;
    public MovieTrailerViewHolder(View itemView, MovieTrailerAdapter movieTrailerAdapter, StartMovieTrailerListener movieTrailerListener, Context context) {
        super(itemView);

        ButterKnife.bind(MovieTrailerViewHolder.this, itemView);
        mContext = new WeakReference<>(context);
        mIvPlayTrailerItem.setOnClickListener(MovieTrailerViewHolder.this);
        mMovieTrailerAdapter = movieTrailerAdapter;
    }

    @Override
    public void onClick(View v) {
        final Trailer trailer = mMovieTrailerAdapter.getTrailerFromPosition(getAdapterPosition());
        final Intent intent = YouTubeStandalonePlayer.createVideoIntent((Activity)itemView.getContext(), Constants.YOUTUBE_API_KEY, trailer.getKey());
        itemView.getContext().startActivity(intent);
    }

    public void setViewData(Trailer trailer) {
        final String youtubeUrl = Constants.YOUTUBE_URL + trailer.getKey() + "/0.jpg";
        Timber.d("setViewData %s: ", youtubeUrl);

        GlideApp.with(mYoutubeThumbnail.getContext())
                .load(youtubeUrl)
                .placeholder(R.drawable.placeholder_poster)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(mYoutubeThumbnail);
    }
}
