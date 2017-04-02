package me.androidbox.busbymovies.moviedetails;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.androidbox.busbymovies.R;
import me.androidbox.busbymovies.adapters.MovieTrailerAdapter;
import me.androidbox.busbymovies.models.Trailer;

/**
 * Created by steve on 3/31/17.
 */

public class MovieTrailerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    @BindView(R.id.tvTrailerName) TextView mTvTrailerName;
    @BindView(R.id.youtubeFragmentContainerItem) FrameLayout mYoutubeFragmentContainerItem;
    @BindView(R.id.ivPlayTrailerItem) ImageView mIvPlayTrailerItem;

    private StartMovieTrailerListener mMovieTrailerListener;
    private MovieTrailerAdapter mMovieTrailerAdapter;

    public MovieTrailerViewHolder(View itemView, MovieTrailerAdapter movieTrailerAdapter, StartMovieTrailerListener movieTrailerListener) {
        super(itemView);

        ButterKnife.bind(MovieTrailerViewHolder.this, itemView);
        mIvPlayTrailerItem.setOnClickListener(MovieTrailerViewHolder.this);

        mMovieTrailerListener = movieTrailerListener;
        mMovieTrailerAdapter = movieTrailerAdapter;
    }

    public void setUpData(Trailer trailer) {
        mTvTrailerName.setText(trailer.getName());
    }

    @Override
    public void onClick(View v) {
        final Trailer trailer = mMovieTrailerAdapter.getTrailerFromPosition(getAdapterPosition());
        mMovieTrailerListener.onStartMovieTrailer(trailer.getKey());
    }
}
