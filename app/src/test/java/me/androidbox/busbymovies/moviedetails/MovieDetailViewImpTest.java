package me.androidbox.busbymovies.moviedetails;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Created by steve on 10/8/17.
 */
public class MovieDetailViewImpTest {
    private MovieDetailViewImp movieDetailViewImp;

    @Before
    public void setup() {
        movieDetailViewImp = new MovieDetailViewImp();
    }

    @Test
    public void testMovieDetailViewImp_shouldNotBeNull() {
        assertNotNull(movieDetailViewImp);
    }
}