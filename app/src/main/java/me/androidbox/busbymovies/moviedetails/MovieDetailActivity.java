package me.androidbox.busbymovies.moviedetails;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import me.androidbox.busbymovies.R;
import timber.log.Timber;

public class MovieDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Timber.d("onBackPressed");

        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            return true;
        }
        
        return super.onOptionsItemSelected(item);
    }
}
