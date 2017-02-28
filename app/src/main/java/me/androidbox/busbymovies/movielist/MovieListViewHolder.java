package me.androidbox.busbymovies.movielist;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.androidbox.busbymovies.R;
import me.androidbox.busbymovies.adapters.MovieAdapter;
import timber.log.Timber;

/**
 * Created by steve on 2/26/17.
 */

public class MovieListViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.ivPosterImage) ImageView mIvPosterImage;
    @BindView(R.id.tvTagLine) TextView mTvTagLine;
    @BindView(R.id.palette) View mPalette;

    private MovieAdapter mMovieAdapter;

    public MovieListViewHolder(View itemView, MovieAdapter movieAdapter) {
        super(itemView);

        ButterKnife.bind(MovieListViewHolder.this, itemView);

        mMovieAdapter = movieAdapter;
    }

    public void bindViewData(String tagline, String posterPath) {
        mTvTagLine.setText(tagline);

    }

    @SuppressWarnings("unused")
    @OnClick(R.id.movieListItem)
    public void onMovieClicked(View view) {
        /* movie has been selected */
        Timber.d("Movie ID: %d", mMovieAdapter.getMovieId(getAdapterPosition()));

        /*

        if(mMovieSelectedListener != null) {
            Timber.d("Movie Adapter position: %d", getAdapterPosition());
            mMovieSelectedListener.onMovieSelected(mMovieId);
        }
        else {
            Timber.e("Failed to get the movie position");
        }
*/
    }
}
