package me.androidbox.busbymovies.utils;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;

import com.bumptech.glide.Priority;

/**
 * Created by steve on 9/19/17.
 */

public interface ImageLoader {
    void load(final Context context,
              final String path,
              final @DrawableRes int placeholder,
              final ImageView imageView,
              final Priority priority);
}

