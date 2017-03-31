package me.androidbox.busbymovies.models;

import java.util.List;

/**
 * Created by steve on 3/31/17.
 */

public class ResultsTrailer<T> {
    private List<T> results;

    public List<T> getMovieTrailers() {
        return results;
    }
}
