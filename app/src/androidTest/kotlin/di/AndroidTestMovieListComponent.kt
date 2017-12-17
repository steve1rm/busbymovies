package di

import dagger.Subcomponent
import me.androidbox.busbymovies.di.MovieListComponent
import me.androidbox.busbymovies.di.scopes.MovieListScope
import me.androidbox.busbymovies.movielist.MovieListViewImpTest

/**
 * Created by steve on 11/29/17.
 */
@MovieListScope
@Subcomponent(modules = arrayOf(MockMovieListModule::class))
interface AndroidTestMovieListComponent : MovieListComponent {
    fun inject(target: MovieListViewImpTest)
}