package me.androidbox.busbymovies.moviesearch

/**
 * Created by steve on 10/1/17.
 */
interface MovieSearchListener {
    fun onMovieSearch(movieName: String, movieYear: Int)
}