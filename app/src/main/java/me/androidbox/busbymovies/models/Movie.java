package me.androidbox.busbymovies.models;

/**
 * Created by steve on 2/20/17.
 */

public class Movie extends Movies {
    private String tagline;
    private String homepage;
    private int runtime;

    public Movie() {
        super();
    }

    public Movie(int id,
                 String poster_path,
                 String overview,
                 String release_date,
                 String title,
                 String backdrop_path,
                 float vote_average) {
        this(id, poster_path, overview, release_date, title, backdrop_path, vote_average, "", "", 0);
    }

    public Movie(int id,
                 String poster_path,
                 String overview,
                 String release_date,
                 String title,
                 String backdrop_path,
                 float vote_average,
                 String tagline,
                 String homepage,
                 int runtime) {
        super(id, poster_path, overview, release_date, title, backdrop_path, vote_average);

        this.tagline = tagline;
        this.homepage = homepage;
        this.runtime = runtime;
    }

    public String getTagline() {
        return tagline;
    }

    public String getHomepage() {
        return homepage;
    }

    public int getRuntime() {
        return runtime;
    }
}
