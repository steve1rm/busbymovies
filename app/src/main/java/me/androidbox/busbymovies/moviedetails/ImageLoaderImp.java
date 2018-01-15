package me.androidbox.busbymovies.moviedetails;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.graphics.Palette;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Priority;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import me.androidbox.busbymovies.utils.GlideApp;
import me.androidbox.busbymovies.utils.ImageLoader;
import me.androidbox.busbymovies.utils.MovieImage;
import timber.log.Timber;

/**
 * Created by steve on 9/19/17.
 */
public final class ImageLoaderImp implements ImageLoader {
    private final Context context;

    public ImageLoaderImp(final Context context) {
        this.context = context;
    }

    @Override
    public void load(final String path,
                     final int placeholder,
                     final ImageView imageView,
                     final Priority priority) {
        GlideApp.with(this.context)
                .load(path)
                .placeholder(placeholder)
                .priority(priority)
                .into(imageView);
    }

    @Override
    public void load(final int targetWidth,
                     final int targetHeight,
                     final int maximumColorCount,
                     final int placeHolder,
                     final ImageView imageView,
                     final String posterPath,
                     final View paletteView,
                     final TextView textContent) {

        final String fullImagePath = MovieImage.build(posterPath, MovieImage.ImageSize.w185);
        Timber.d("%s - %s", textContent, fullImagePath);

        Picasso.with(this.context)
                .load(fullImagePath)
                .resize(200, 300)
                .centerCrop()
                .placeholder(placeHolder)
                .error(placeHolder)
                .into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        imageView.setImageBitmap(bitmap);
                        Palette.from(bitmap)
                                .maximumColorCount(maximumColorCount)
                                .generate(palette -> {
                                    Palette.Swatch titleBackground = palette.getDarkVibrantSwatch();
                                    if(titleBackground != null) {
                                        paletteView.setBackgroundColor(titleBackground.getRgb());
                                        textContent.setTextColor(titleBackground.getBodyTextColor());
                                    }
                                    else {
                                        Timber.e("titleBackground == null");
                                    }
                                });
                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {
                        Timber.e("onBitmapFailed");
                        imageView.setImageDrawable(errorDrawable);
                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {
                        Timber.d("onPrepareLoad");
                        imageView.setImageDrawable(placeHolderDrawable);
                    }
                });
    }
}
