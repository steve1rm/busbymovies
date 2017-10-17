package me.androidbox.busbymovies.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import me.androidbox.busbymovies.R
import me.androidbox.busbymovies.models.Movies
import me.androidbox.busbymovies.models.Results
import me.androidbox.busbymovies.moviedetails.SimilarMovieViewHolder

/**
 * Created by steve on 10/8/17.
 */
class SimilarMovieAdapter : RecyclerView.Adapter<SimilarMovieViewHolder>() {

    private lateinit var movieList: Results<Movies>

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): SimilarMovieViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.similar_movie_item, parent, false)

        return SimilarMovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: SimilarMovieViewHolder?, position: Int) {
        holder?.assignSimilarMovieImage(movieList.results[position])
    }

    override fun getItemCount(): Int {
        return movieList.results.size
    }

    fun loadAdapter(movies: Results<Movies>) {
        movieList.results.clear()
        movieList.results.addAll(movies.results)

        notifyItemRangeInserted(0, movieList.results.size)
    }
}
