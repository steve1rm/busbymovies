package me.androidbox.busbymovies.moviedetails;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Priority;

import me.androidbox.busbymovies.utils.GlideApp;

/**
 * Created by steve on 9/19/17.
 */
public class ImageLoaderImp implements ImageLoader {
    public ImageLoaderImp() {}

    @Override
    public void load(final Context context, final String path, final int placeholder, final ImageView imageView, final Priority priority) {
        GlideApp.with(context)
                .load(path)
                .placeholder(placeholder)
                .priority(priority)
                .into(imageView);
    }
}
