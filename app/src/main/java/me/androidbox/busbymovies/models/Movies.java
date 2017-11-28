package me.androidbox.busbymovies.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by steve on 3/11/17.
 */

public class Movies {
    private int id;
    @SerializedName("poster_path")
    private String posterPath;
    private String overview;
    @SerializedName("release_date")
    private String releaseDate;
    private String title;
    @SerializedName("backdrop_path")
    private String backdropPath;
    @SerializedName("vote_average")
    private float voteAverage;
    @SerializedName("vote_count")
    private float voteCount;

    public Movies() {}

    public Movies(int id,
                  String poster_path,
                  String overview,
                  String release_date,
                  String title,
                  String backdrop_path,
                  float vote_average,
                  float vote_count) {
        this.id = id;
        this.posterPath = poster_path;
        this.overview = overview;
        this.releaseDate = release_date;
        this.title = title;
        this.backdropPath = backdrop_path;
        this.voteAverage = vote_average;
        this.voteCount = vote_count;
    }

    public int getId() {
        return id;
    }

    public String getPoster_path() {
        return posterPath;
    }

    public String getOverview() {
        return overview;
    }

    public String getRelease_date() {
        return releaseDate;
    }

    public String getTitle() {
        return title;
    }

    public String getBackdrop_path() {
        return backdropPath;
    }

    public float getVote_average() {
        return voteAverage;
    }

    public float getVote_count() {
        return voteCount;
    }
}
