package me.androidbox.busbymovies.movielist;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Priority;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.androidbox.busbymovies.R;
import me.androidbox.busbymovies.adapters.MovieAdapter;
import me.androidbox.busbymovies.moviedetails.MovieDetailActivity;
import me.androidbox.busbymovies.moviedetails.MovieDetailViewImp;
import me.androidbox.busbymovies.utils.ImageLoader;
import me.androidbox.busbymovies.utils.MovieImage;
import me.androidbox.busbymovies.utils.MovieImage.ImageSize;
import timber.log.Timber;

/**
 * Created by steve on 2/26/17.
 */
public class MovieListViewHolder
        extends RecyclerView.ViewHolder {

    @BindView(R.id.ivPosterImage) ImageView mIvPosterImage;
    @BindView(R.id.tvTagLine) TextView mTvTagLine;
    @BindView(R.id.palette) View mPalette;

    private ImageLoader imageLoader;

    private MovieAdapter mMovieAdapter;
    /* Ensure the context is garbage collected so using a weak reference */
    private WeakReference<Context> mContext;

    public MovieListViewHolder(View itemView, MovieAdapter movieAdapter, final ImageLoader imageLoader) {
        super(itemView);

        ButterKnife.bind(MovieListViewHolder.this, itemView);
        mContext = new WeakReference<>(itemView.getContext());
        mMovieAdapter = movieAdapter;
        this.imageLoader = imageLoader;
    }

    public void bindViewData(String tagline, String posterPath) {
        mTvTagLine.setText(tagline);

        final String fullImagePath = MovieImage.build(posterPath, ImageSize.w185);
        Timber.d("%s - %s", tagline, fullImagePath);

        imageLoader.load(
                mContext.get(),
                200,
                300,
                16,
                R.drawable.peopleplaceholder,
                mIvPosterImage,
                fullImagePath,
                mPalette,
                mTvTagLine);
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.movieListItem)
    public void onMovieClicked(View view) {
        /* movie has been selected */
        int movieId = mMovieAdapter.getMovieId(getAdapterPosition());
        Timber.d("Movie ID: %d", movieId);

        final Intent intent = new Intent(mContext.get(), MovieDetailActivity.class);
        intent.putExtra(MovieDetailViewImp.MOVIE_ID_KEY, movieId);
        mContext.get().startActivity(intent);
        ((Activity)mContext.get()).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}
