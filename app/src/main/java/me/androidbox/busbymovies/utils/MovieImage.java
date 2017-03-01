package me.androidbox.busbymovies.utils;

import static me.androidbox.busbymovies.utils.MovieImage.ImageSize.w154;
import static me.androidbox.busbymovies.utils.MovieImage.ImageSize.w185;
import static me.androidbox.busbymovies.utils.MovieImage.ImageSize.w342;
import static me.androidbox.busbymovies.utils.MovieImage.ImageSize.w500;
import static me.androidbox.busbymovies.utils.MovieImage.ImageSize.w700;
import static me.androidbox.busbymovies.utils.MovieImage.ImageSize.w92;

/**
 * Created by steve on 2/28/17.
 */

public class MovieImage {

    /* bHarw8xrmQeqf3t8HpuMY7zoK4x.jpg */
    /* https://image.tmdb.org/t/p/w500/ */
    /* https://image.tmdb.org/t/p/w500/bHarw8xrmQeqf3t8HpuMY7zoK4x.jpg */

    public static String build(String imagePath,  ImageSize imageSize) {
        final StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(Constants.IMAGE_URL);

        switch(imageSize) {
            case w92:
                stringBuilder.append(String.valueOf(w92));
                break;

            case w154:
                stringBuilder.append(String.valueOf(w154));
                break;

            case w185:
                stringBuilder.append(String.valueOf(w185));
                break;

            case w342:
                stringBuilder.append(String.valueOf(w342));
                break;

            case w500:
                stringBuilder.append(String.valueOf(w500));
                break;

            case w700:
                stringBuilder.append(String.valueOf(w700));
                break;

            default:
                stringBuilder.append(String.valueOf(w185));
                break;
        }

        stringBuilder.append(imagePath);

        return stringBuilder.toString();
    }

    public enum ImageSize {
        w92, w154, w185, w342, w500, w700;
    }
}
