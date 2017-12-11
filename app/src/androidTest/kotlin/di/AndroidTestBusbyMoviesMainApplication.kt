package di

import me.androidbox.busbymovies.di.BusbyMoviesMainApplication

/**
 * Created by steve on 11/29/17.
 */
class AndroidTestBusbyMoviesMainApplication : BusbyMoviesMainApplication() {
    private lateinit var androidTestBusbyMoviesComponent: AndroidTestBusbyMoviesAppComponent
    private lateinit var androidTestBusbyMovieListComponent: AndroidTestMovieListComponent

    override fun createAppComponent(): AndroidTestBusbyMoviesAppComponent {
        androidTestBusbyMoviesComponent = createAndroidTestBusbyMoviesComponent()
        androidTestBusbyMovieListComponent = createAndroidTestBusbyMovieListComponent()

        return androidTestBusbyMoviesComponent;
    }

    private fun createAndroidTestBusbyMoviesComponent(): AndroidTestBusbyMoviesAppComponent {
        androidTestBusbyMoviesComponent = DaggerAndroidTestBusbyMoviesAppComponent.builder().build()

        return androidTestBusbyMoviesComponent
    }

    override fun getMovieListComponent(): AndroidTestMovieListComponent {
        return androidTestBusbyMovieListComponent
    }

    private fun createAndroidTestBusbyMovieListComponent(): AndroidTestMovieListComponent {
        return androidTestBusbyMoviesComponent.add(MockMovieListModule())
    }
}
