package me.androidbox.busbymovies.models;

/**
 * Created by steve on 9/16/17.
 */

public class Actor {
    private String profile_path;
    private String name;
    private String character;

    public Actor(String picturePath, String name, String character) {
        this.profile_path = picturePath;
        this.name = name;
        this.character = character;
    }

    public String getProfile_path() {
        return profile_path;
    }

    public String getName() {
        return name;
    }

    public String getCharacter() {
        return character;
    }
}
