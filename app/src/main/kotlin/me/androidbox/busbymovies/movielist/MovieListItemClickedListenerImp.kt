package me.androidbox.busbymovies.movielist

import android.content.Intent
import me.androidbox.busbymovies.R
import me.androidbox.busbymovies.moviedetails.MovieDetailActivity
import me.androidbox.busbymovies.moviedetails.MovieDetailViewImp

class MovieListItemClickedListenerImp(private val movieListActivity: MovieListActivity): MovieListItemClickedListener {
    override fun onMovieListItemClickedListener(movieId: Int) {
        val intent = Intent(movieListActivity, MovieDetailActivity::class.java)
        intent.putExtra(MovieDetailViewImp.MOVIE_ID_KEY, movieId)

        movieListActivity.startActivity(intent)
        movieListActivity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }
}