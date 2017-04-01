package me.androidbox.busbymovies.moviedetails;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.androidbox.busbymovies.R;

/**
 * Created by steve on 3/31/17.
 */

public class MovieTrailerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public @BindView(R.id.tvTrailerName) TextView mTvTrailerName;
    public @BindView(R.id.youtubeFragmentContainerItem) FrameLayout mYoutubeFragmentContainerItem;
    public @BindView(R.id.ivPlayTrailerItem) ImageView mIvPlayTrailerItem;

    private MovieTrailerListener mMovieTrailerListener;

    public MovieTrailerViewHolder(View itemView, MovieTrailerListener movieTrailerListener) {
        super(itemView);

        ButterKnife.bind(MovieTrailerViewHolder.this, itemView);
        mIvPlayTrailerItem.setOnClickListener(MovieTrailerViewHolder.this);
        mMovieTrailerListener = movieTrailerListener;
    }

    @Override
    public void onClick(View v) {
        mMovieTrailerListener.onMovieTrailer("");
    }
}
