package me.androidbox.busbymovies.di

/**
 * Created by steve on 11/29/17.
 */
class AndroidTestBusbyMoviesMainApplication : BusbyMoviesMainApplication() {
    private lateinit var androidTestBusbyMoviesComponent: AndroidTestBusbyMoviesAppComponent


    override fun createAppComponent(): AndroidTestBusbyMoviesAppComponent {
        androidTestBusbyMoviesComponent = createAndroidTestBusbyMoviesComponent()

        return androidTestBusbyMoviesComponent;
    }

    private fun createAndroidTestBusbyMoviesComponent(): AndroidTestBusbyMoviesAppComponent {
        androidTestBusbyMoviesComponent = DaggerAndroidTestBusbyMoviesAppComponent.builder().build()

        return androidTestBusbyMoviesComponent
    }
}