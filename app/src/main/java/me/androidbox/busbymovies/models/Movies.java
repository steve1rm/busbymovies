package me.androidbox.busbymovies.models;

/**
 * Created by steve on 3/11/17.
 */

public class Movies {
    private int id;
    private String poster_path;
    private String overview;
    private String release_date;
    private String title;
    private String backdrop_path;
    private float vote_average;

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

}
