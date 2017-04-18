package me.androidbox.busbymovies.models;

/**
 * Created by steve on 4/9/17.
 */

public class Review {
    private String id;
    private String author;
    private String content;
    private String url;

    public Review() {
        /* no-op for */
    }

    public String getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public String getUrl() {
        return url;
    }
}
