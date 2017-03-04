package me.androidbox.busbymovies.models;

/**
 * Created by steve on 2/20/17.
 */

public class Movie {
    private int id;
    private String poster_path;
    private String overview;
    private String release_date;
    private String title;
    private String backdrop_path;
    private float vote_average;
    private String tagline;
    private String homepage;
    private int runtime;

    public int getId() {
        return id;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public String getOverview() {
        return overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public String getTitle() {
        return title;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public float getVote_average() {
        return vote_average;
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
