package me.androidbox.busbymovies.moviedetails;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;

/**
 * Created by steve on 9/19/17.
 */

public interface ImageLoader {
    void load(final Context context,
              final String path,
              final @DrawableRes int placeholder,
              final ImageView imageView);
}
