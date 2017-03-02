package me.androidbox.busbymovies.models;

/**
 * Created by steve on 2/18/17.
 */

public class TopRated {
    private String poster_path;
    private String overview;
    private String release_date;
    private String title;
    private String backdrop_path;
    private int vote_average;

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

    public int getVote_average() {
        return vote_average;
    }

}
