package me.androidbox.busbymovies.support

import me.androidbox.busbymovies.di.DaggerTestBusbyMovieAppComponent
import me.androidbox.busbymovies.di.TestAndroidModule
import me.androidbox.busbymovies.di.TestBusbyMovieAppComponent

/**
 * Created by steve on 11/22/17.
 */
/*
@Config(sdk = intArrayOf(Build.VERSION_CODES.LOLLIPOP),
        constants = BuildConfig::class,
        packageName = "me.androidbox.busbymovies",
        application = BusbyMoviesMainApplication::class)
@RunWith(RobolectricTestRunner::class)
*/
open class BaseRobolectricTestRunner {
    fun getTestBusbyMovieAppComponent() : TestBusbyMovieAppComponent =
            DaggerTestBusbyMovieAppComponent
                    .builder()
                    .testAndroidModule(TestAndroidModule())
                    .build()
}
