package me.androidbox.busbymovies.utils;

/**
 * Created by steve on 2/18/17.
 */

public final class Constants {
    public static final String MOVIES_API_KEY = "548c69a01f777b7f16db88b053bbc903"; /* ENTER OUR/YOUR API HERE */
    public static final String YOUTUBE_API_KEY = "AIzaSyBKQN1qEQAouJ-xUgtbyLg433VrlqD_pxo";
    public static final String BASE_URL = "https://api.themoviedb.org/3/";
    public static final String IMAGE_URL = "https://image.tmdb.org/t/p/";
    public static final String YOUTUBE_URL = "http://img.youtube.com/vi/";
    public static final String FORMAT_MOVIE_DATE = "dd MMM yyyy";


    public enum  MovieListViewHolderType {
        NORMAL(1);

        private final int type;

        MovieListViewHolderType(int type) {
            this.type = type;
        }

        public int getMovieListViewHolderType() {
            return this.type;
        }
    }
}