package me.androidbox.busbymovies.moviedetails;

import android.content.Context;
import android.widget.ImageView;

import me.androidbox.busbymovies.utils.GlideApp;

/**
 * Created by steve on 9/19/17.
 */

public class ImageLoaderImp implements ImageLoader {
    public ImageLoaderImp() {}

    @Override
    public void load(final Context context, final String path, final int placeholder, final ImageView imageView) {
        GlideApp.with(context)
                .load(path)
                .placeholder(placeholder)
                .into(imageView);
    }
}
