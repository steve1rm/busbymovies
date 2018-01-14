package me.androidbox.busbymovies.movielist

import android.support.v7.widget.RecyclerView
import android.view.View
import me.androidbox.busbymovies.utils.ImageLoader

interface IMovieListViewHolderFactory {
    fun createViewHolder(parent: View, imageLoader: ImageLoader): RecyclerView.ViewHolder
}
