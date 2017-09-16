package me.androidbox.busbymovies.models;

import java.util.List;

/**
 * Created by steve on 9/16/17.
 */

public class Cast<T> {
    private List<T> cast;

    public Cast(List<T> cast) {
        this.cast = cast;
    }

    public List<T> getCast() {
        return cast;
    }
}
