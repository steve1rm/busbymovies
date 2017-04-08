package me.androidbox.busbymovies.moviedetails;

import android.widget.FrameLayout;
import android.widget.ImageView;

/**
 * Created by steve on 3/31/17.
 */

public interface StartMovieTrailerListener {
    void onStartMovieTrailer(String key, FrameLayout youtube, ImageView imageView);
}
