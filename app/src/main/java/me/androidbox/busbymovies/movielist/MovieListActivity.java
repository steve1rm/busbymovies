package me.androidbox.busbymovies.movielist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import me.androidbox.busbymovies.R;

public class MovieListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.movielist_container, MovieListView.newInstance(), MovieListView.TAG)
                    .commit();
        }
    }
}
