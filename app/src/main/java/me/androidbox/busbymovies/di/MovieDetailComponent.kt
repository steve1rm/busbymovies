package me.androidbox.busbymovies.di

import dagger.Subcomponent
import me.androidbox.busbymovies.di.scopes.MovieDetailScope
import me.androidbox.busbymovies.moviedetails.MovieActorsViewHolder
import me.androidbox.busbymovies.moviedetails.MovieDetailModelImp
import me.androidbox.busbymovies.moviedetails.MovieDetailViewImp

/**
 * Created by steve on 10/26/17.
 */
@MovieDetailScope
@Subcomponent(modules = arrayOf(MovieDetailModule::class))
interface MovieDetailComponent {
    fun inject(target: MovieDetailModelImp)
    fun inject(target: MovieDetailViewImp)
    fun inject(target: MovieActorsViewHolder)
}
