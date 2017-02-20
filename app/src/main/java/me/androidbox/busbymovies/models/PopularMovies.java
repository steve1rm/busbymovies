package me.androidbox.busbymovies.models;

import java.util.List;

/**
 * Created by steve on 2/18/17.
 */

public class PopularMovies {
    private List<Popular> results;

    public List<Popular> getResults() {
        return results;
    }

    public void setResults(List<Popular> results) {
        this.results = results;
    }

    public static class Popular {
        private String poster_path;
        private String overview;
        private String release_date;
        private String title;
        private String backdrop_path;
        private float vote_average;

        public String getPoster_path() {
            return poster_path;
        }

        public void setPoster_path(String poster_path) {
            this.poster_path = poster_path;
        }

        public String getOverview() {
            return overview;
        }

        public void setOverview(String overview) {
            this.overview = overview;
        }

        public String getRelease_date() {
            return release_date;
        }

        public void setRelease_date(String release_date) {
            this.release_date = release_date;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getBackdrop_path() {
            return backdrop_path;
        }

        public void setBackdrop_path(String backdrop_path) {
            this.backdrop_path = backdrop_path;
        }

        public float getVote_average() {
            return vote_average;
        }

        public void setVote_average(float vote_average) {
            this.vote_average = vote_average;
        }

    }
}