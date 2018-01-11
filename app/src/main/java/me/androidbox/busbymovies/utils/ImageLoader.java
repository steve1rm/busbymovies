package me.androidbox.busbymovies.utils;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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

    void load(final Context context,
              final int targetWidth,
              final int targetHeight,
              final int maximumColorCount,
              final int placeHolder,
              final ImageView imageView,
              final String posterPath,
              final View palette,
              final TextView textContent);
}

