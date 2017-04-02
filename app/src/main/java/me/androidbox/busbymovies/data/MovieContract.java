package me.androidbox.busbymovies.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by steve on 3/25/17.
 */

public final class MovieContract {
    private MovieContract() {}

    public static final String SCHEME = "content://";
    public static final String AUTHORITY = "me.androidbox.busbymovies";
    public static final Uri BASE_CONTENT_URI = Uri.parse(SCHEME + AUTHORITY);
    public static final String PATH_MOVIE = "movie";

    public final static class MovieEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIE).build();

        public static final String TABLE_NAME = "movie";
        public static final String MOVIE_ID = "movieId";
        public static final String POSTER_PATH = "poster_path";
        public static final String RELEASE_DATE = "release_date";
        public static final String TITLE = "title";
        public static final String BACKDROP_PATH = "backdrop_path";
        public static final String VOTE_AVERAGE = "vote_average";
        public static final String TAGLINE = "tagline";
        public static final String HOMEPATH = "homepage";
        public static final String RUNTIME = "runtime";
    }
}
