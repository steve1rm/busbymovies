package di

import dagger.Component
import me.androidbox.busbymovies.moviedetails.MovieDetailModelImpTest
import me.androidbox.busbymovies.movielist.MovieListModelImpTest
import me.androidbox.busbymovies.movielist.MovieListViewImpTest
import javax.inject.Singleton

/**
 * Created by steve on 10/23/17.
 */
@Singleton
@Component(modules = arrayOf(TestAndroidModule::class, TestMovieListModule::class))
interface TestBusbyMoviesAppComponent {
    fun inject(target: MovieListModelImpTest)
    fun inject(target: MovieDetailModelImpTest)
    fun inject(target: MovieListViewImpTest)
}
