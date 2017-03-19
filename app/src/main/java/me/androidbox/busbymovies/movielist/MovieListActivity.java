package me.androidbox.busbymovies.movielist;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import me.androidbox.busbymovies.R;

public class MovieListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

/*
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            final Window window = getWindow();
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
*/

        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.movielist_container, MovieListViewImp.newInstance(), MovieListViewImp.TAG)
                    .commit();
        }
    }
}
