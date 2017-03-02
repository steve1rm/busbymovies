package me.androidbox.busbymovies.moviedetails;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import me.androidbox.busbymovies.R;


public class MovieDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            final Window window = getWindow();
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        if(savedInstanceState == null) {
            final Intent intent = getIntent();

            if(intent.hasExtra(MovieDetailViewImp.MOVIE_ID_KEY)) {
                final int movie_id = intent.getIntExtra(MovieDetailViewImp.MOVIE_ID_KEY, -1);
                if(movie_id != -1) {
                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.moviedetail_container, MovieDetailViewImp.newInstance(movie_id), MovieDetailViewImp.TAG)
                            .commit();
                }
                else {
                    Toast.makeText(this, "No movie to display", Toast.LENGTH_LONG).show();
                }
            }
            else {
                Toast.makeText(this, "No movie to display", Toast.LENGTH_LONG).show();
            }
        }
    }
}
