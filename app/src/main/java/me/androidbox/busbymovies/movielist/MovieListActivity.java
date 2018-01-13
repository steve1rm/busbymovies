package me.androidbox.busbymovies.movielist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import me.androidbox.busbymovies.R;
import me.androidbox.busbymovies.moviedetails.MovieDetailActivity;
import me.androidbox.busbymovies.moviedetails.MovieDetailViewImp;

public class MovieListActivity
        extends
        AppCompatActivity
        implements
        MovieClickedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.movielist_container, MovieListViewImp.newInstance(), MovieListViewImp.TAG)
                    .commit();
        }
    }

    @Override
    public void onMovieClicked(final int movieId) {
        final Intent intent = new Intent(MovieListActivity.this, MovieDetailActivity.class);
        intent.putExtra(MovieDetailViewImp.MOVIE_ID_KEY, movieId);

        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}
