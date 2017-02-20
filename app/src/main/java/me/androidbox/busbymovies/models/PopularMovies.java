package me.androidbox.busbymovies.models;

import java.util.List;

/**
 * Created by steve on 2/18/17.
 */

public class PopularMovies {
    private int page;
    private List<Popular> results;
    private int total_pages;
    private int total_results;

    public PopularMovies(int page, List<Popular> results, int total_pages, int total_results) {
        this.page = page;
        this.results = results;
        this.total_pages = total_pages;
        this.total_results = total_results;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<Popular> getResults() {
        return results;
    }

    public void setResults(List<Popular> results) {
        this.results = results;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public int getTotal_results() {
        return total_results;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }

    public static class Popular {
        private String poster_path;
        private String overview;
        private String release_date;
        private String title;
        private String backdrop_path;
        private float vote_average;

        public Popular(String poster_path,
                       String overview,
                       String release_date,
                       String title,
                       String backdrop_path,
                       float vote_average) {
            this.poster_path = poster_path;
            this.overview = overview;
            this.release_date = release_date;
            this.title = title;
            this.backdrop_path = backdrop_path;
            this.vote_average = vote_average;
        }

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