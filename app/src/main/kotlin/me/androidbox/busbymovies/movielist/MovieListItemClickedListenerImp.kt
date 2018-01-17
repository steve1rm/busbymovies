package me.androidbox.busbymovies.movielist

import android.app.Activity
import android.content.Intent
import me.androidbox.busbymovies.R
import me.androidbox.busbymovies.moviedetails.MovieDetailActivity
import me.androidbox.busbymovies.moviedetails.MovieDetailViewImp

class MovieListItemClickedListenerImp(private val activity: Activity): MovieListItemClickedListener {
    override fun onMovieListItemClickedListener(movieId: Int) {
        val intent = Intent(activity, MovieDetailActivity::class.java)
        intent.putExtra(MovieDetailViewImp.MOVIE_ID_KEY, movieId)

        activity.startActivity(intent)
        activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }
}