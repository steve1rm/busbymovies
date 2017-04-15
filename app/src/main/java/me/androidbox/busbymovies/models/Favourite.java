package me.androidbox.busbymovies.models;

/**
 * Created by steve on 3/26/17.
 */

public class Favourite extends Movies {
    private String mTagline;
    private String mHomepage;
    private int mRuntime;

    public Favourite(int movieId,
                     String posterPath,
                     String overview,
                     String releaseDate,
                     String title,
                     String backdropPath,
                     float voteAverage) {
        this(movieId, posterPath, overview, releaseDate, title, backdropPath, voteAverage, "", "", -1);
    }

    public Favourite(int movieId,
                     String posterPath,
                     String overview,
                     String releaseDate,
                     String title,
                     String backdropPath,
                     float voteAverage,
                     String tagline,
                     String homepage,
                     int runtime) {
        super(movieId, posterPath, overview, releaseDate, title, backdropPath, voteAverage);

        this.mTagline = tagline;
        this.mHomepage = homepage;
        this.mRuntime = runtime;
    }

    public String getTagline() {
        return mTagline;
    }

    public String getHomepage() {
        return mHomepage;
    }

    public int getRuntime() {
        return mRuntime;
    }
}
