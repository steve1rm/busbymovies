package me.androidbox.busbymovies.di

/**
 * Created by steve on 11/29/17.
 */
class TestBusbyMoviesMainApplication : BusbyMoviesMainApplication() {
    private lateinit var appComponent: TestBusbyMoviesAppComponent

   /* fun createAppComponent(): BusbyMoviesAppComponent {
        return DaggerBusbyMoviesAppComponent.builder()
                .apiModule(ApiModule(this))
                .androidModule(AndroidModule(this))
                .build()
    }
*/

  /*  override
    fun createAppComponent(): TestBusbyMoviesAppComponent {
        return super.createAppComponent()
    }*/

    override fun createAppComponent(): TestBusbyMoviesAppComponent {

        DaggerTestBusbyMovieAppComponent
        return null!!
    }
}