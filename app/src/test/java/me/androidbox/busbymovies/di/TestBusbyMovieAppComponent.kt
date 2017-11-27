package me.androidbox.busbymovies.di

import dagger.Component
import me.androidbox.busbymovies.moviedetails.MovieDetailModelImpTest
import me.androidbox.busbymovies.movielist.MovieListModelImpTest
import javax.inject.Singleton

/**
 * Created by steve on 10/23/17.
 */
@Singleton
@Component(modules = arrayOf(TestAndroidModule::class))
interface TestBusbyMovieAppComponent {
    fun inject(target: MovieListModelImpTest)
    fun inject(target: MovieDetailModelImpTest)
}
