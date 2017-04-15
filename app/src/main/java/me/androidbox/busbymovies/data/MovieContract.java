package me.androidbox.busbymovies.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by steve on 3/25/17.
 */

public final class MovieContract {
    private MovieContract() {}

    /* Protocol scheme used */
    public static final String SCHEME = "content://";
    /* The authority, which is how your code knows which content provider to access */
    public static final String AUTHORITY = "me.androidbox.busbymovies";
    /* The base content URI = scheme + authority */
    public static final Uri BASE_CONTENT_URI = Uri.parse(SCHEME + AUTHORITY);
    /* Define the possible paths for accessing data in this contract -
     * this is the path to the movie directory */
    public static final String PATH_MOVIE = "movie";

    public final static class MovieEntry implements BaseColumns {
        private MovieEntry() { /* no-op */ }

        /* MovieEntry content URI = base content URI + path */
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
