package me.androidbox.busbymovies.moviedetails

import androidx.recyclerview.widget.RecyclerView
import android.view.View
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.android.synthetic.main.similar_movie_item.view.*
import me.androidbox.busbymovies.R
import me.androidbox.busbymovies.models.Movies
import me.androidbox.busbymovies.utils.GlideApp
import me.androidbox.busbymovies.utils.MovieImage

/**
 * Created by steve on 10/8/17.
 */
class SimilarMovieViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
    private val similarMovieThumbnail = itemView.ivSimilarMovieThumbnail

    fun assignSimilarMovieImage(movie: Movies) {
        GlideApp.with(itemView.context)
                .load(MovieImage.build(movie.poster_path, MovieImage.ImageSize.w92))
                .placeholder(R.drawable.placeholder_poster)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(similarMovieThumbnail)
    }
}