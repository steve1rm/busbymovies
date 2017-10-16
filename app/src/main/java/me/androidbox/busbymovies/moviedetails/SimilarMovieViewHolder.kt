package me.androidbox.busbymovies.moviedetails

import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import me.androidbox.busbymovies.R
import me.androidbox.busbymovies.models.Movies
import me.androidbox.busbymovies.utils.GlideApp

/**
 * Created by steve on 10/8/17.
 */
class SimilarMovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private lateinit var similarMovieThumbnail: ImageView

    fun assignSimilarMovieImage(movie: Movies) : Unit {
        GlideApp.with(itemView.context)
                .load(movie.poster_path)
                .placeholder(R.drawable.placeholder_poster)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(similarMovieThumbnail)

        similarMovieThumbnail.setImageURI(Uri.EMPTY)
    }
}