package me.androidbox.busbymovies.models;

/**
 * Created by steve on 3/26/17.
 */

public class Favourite {
    private int mMovieId;
    private String mPosterPath;
    private String mReleaseData;
    private String mTitle;
    private String mBackdropPath;
    private float mVoteAverage;
    private String mTagline;
    private String mHomepage;
    private int mRuntime;

    public Favourite(int movieId,
                     String posterPath,
                     String releaseData,
                     String title,
                     String backdropPath,
                     float voteAverage,
                     String tagline,
                     String homepage,
                     int runtime) {
        this.mMovieId = movieId;
        this.mPosterPath = posterPath;
        this.mReleaseData = releaseData;
        this.mTitle = title;
        this.mBackdropPath = backdropPath;
        this.mVoteAverage = voteAverage;
        this.mTagline = tagline;
        this.mHomepage = homepage;
        this.mRuntime = runtime;
    }

    public int getMovieId() {
        return mMovieId;
    }

    public String getPosterPath() {
        return mPosterPath;
    }

    public String getReleaseData() {
        return mReleaseData;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getBackdropPath() {
        return mBackdropPath;
    }

    public float getVoteAverage() {
        return mVoteAverage;
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
