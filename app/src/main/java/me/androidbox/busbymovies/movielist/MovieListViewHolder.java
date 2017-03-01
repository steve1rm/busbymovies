package me.androidbox.busbymovies.movielist;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.androidbox.busbymovies.R;
import me.androidbox.busbymovies.adapters.MovieAdapter;
import me.androidbox.busbymovies.utils.MovieImage;
import timber.log.Timber;

/**
 * Created by steve on 2/26/17.
 */

public class MovieListViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.ivPosterImage) ImageView mIvPosterImage;
    @BindView(R.id.tvTagLine) TextView mTvTagLine;
    @BindView(R.id.palette) View mPalette;

    private MovieAdapter mMovieAdapter;
    /* Ensure the context is garbage collected so using a weak reference */
    private WeakReference<Context> mContext;

    public MovieListViewHolder(View itemView, MovieAdapter movieAdapter, Context context) {
        super(itemView);

        ButterKnife.bind(MovieListViewHolder.this, itemView);
        mContext = new WeakReference<>(context);
        mMovieAdapter = movieAdapter;
    }

    public void bindViewData(String tagline, String posterPath) {
        mTvTagLine.setText(tagline);

        final String fullImagePath = MovieImage.build(posterPath, MovieImage.ImageSize.w185);
        Timber.d("%s - %s", tagline, fullImagePath);

        // Picasso.with(mContext.get()).load(fullImagePath).into(mIvPosterImage);

        Picasso.with(mContext.get())
                .load(fullImagePath)
                .resize(200, 300)
                .centerCrop()
                .into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        mIvPosterImage.setImageBitmap(bitmap);
                        Palette.from(bitmap)
                                .maximumColorCount(12)
                                .generate(new Palette.PaletteAsyncListener() {
                                    @Override
                                    public void onGenerated(Palette palette) {

                                        Palette.Swatch titleBackground = palette.getDarkVibrantSwatch();
                                        if(titleBackground != null) {
                                            mPalette.setBackgroundColor(titleBackground.getRgb());
                                            mTvTagLine.setTextColor(titleBackground.getBodyTextColor());
                                        }
                                        else {
                                            Timber.e("titleBackground == null");
                                        }
                                    }
                                });
                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {
                        Timber.e("onBitmapFailed");
                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {
                        Timber.d("onPrepareLoad");
                    }
                });
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
