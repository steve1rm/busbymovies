package me.androidbox.busbymovies.moviedetails;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeStandalonePlayer;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.androidbox.busbymovies.R;
import me.androidbox.busbymovies.adapters.MovieTrailerAdapter;
import me.androidbox.busbymovies.models.Trailer;
import me.androidbox.busbymovies.utils.Constants;
import timber.log.Timber;

/**
 * Created by steve on 3/31/17.
 */

public class MovieTrailerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    @BindView(R.id.youtubeThumbnail) YouTubeThumbnailView mYoutubeThumbnail;
    @BindView(R.id.ivPlayTrailerItem) ImageView mIvPlayTrailerItem;

//    private StartMovieTrailerListener mMovieTrailerListener;
    private MovieTrailerAdapter mMovieTrailerAdapter;

    public MovieTrailerViewHolder(View itemView, MovieTrailerAdapter movieTrailerAdapter, StartMovieTrailerListener movieTrailerListener) {
        super(itemView);

        ButterKnife.bind(MovieTrailerViewHolder.this, itemView);

        mIvPlayTrailerItem.setOnClickListener(MovieTrailerViewHolder.this);

     //   mMovieTrailerListener = movieTrailerListener;
        mMovieTrailerAdapter = movieTrailerAdapter;
    }

    @Override
    public void onClick(View v) {

        final Trailer trailer = mMovieTrailerAdapter.getTrailerFromPosition(getAdapterPosition());
//        mMovieTrailerListener.onStartMovieTrailer(trailer.getKey(), mYoutubeFragmentContainerItem, mIvPlayTrailerItem);

        final Intent intent = YouTubeStandalonePlayer.createVideoIntent((Activity)itemView.getContext(), Constants.YOUTUBE_API_KEY, trailer.getKey());
        itemView.getContext().startActivity(intent);
    }

    public void setViewData(Trailer trailer) {
        mYoutubeThumbnail.initialize(Constants.YOUTUBE_API_KEY, new YouTubeThumbnailView.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader youTubeThumbnailLoader) {
                Timber.d("onInitializationSuccess");

                youTubeThumbnailLoader.setVideo(trailer.getKey());
            //    youTubeThumbnailLoader.release();
            }

            @Override
            public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {
                if(youTubeInitializationResult.isUserRecoverableError()) {
                    Timber.e("isUserRecoverableError");
                }
                else {
                    Timber.e("NOT isUserRecoverableError");
                }
                Timber.e("onInitializationFailure %s", youTubeInitializationResult.toString());
            }
        });
    }
}
