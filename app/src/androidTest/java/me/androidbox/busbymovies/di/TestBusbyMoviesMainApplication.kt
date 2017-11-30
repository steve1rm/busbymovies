package me.androidbox.busbymovies.di

/**
 * Created by steve on 11/29/17.
 */
class TestBusbyMoviesMainApplication : BusbyMoviesMainApplication() {
    private lateinit var appComponent: AndroidTestBusbyMoviesAppComponent


  /*  fun createAppComponent(): BusbyMoviesAppComponent {
        return DaggerBusbyMoviesAppComponent.builder()
                .apiModule(ApiModule(this))
                .androidModule(AndroidModule(this))
                .build()
    }*/

    override fun createAppComponent(): AndroidTestBusbyMoviesAppComponent {
        return DaggerAndroidTestBusbyMoviesAppComponent.builder()
                .androidModule(AndroidModule(this))
                .apiModule(ApiModule(this))
                .build()
    }
}