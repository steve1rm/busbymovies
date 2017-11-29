package me.androidbox.busbymovies.support

import android.os.Build
import me.androidbox.busbymovies.BuildConfig
import me.androidbox.busbymovies.di.BusbyMoviesMainApplication
import me.androidbox.busbymovies.di.DaggerTestBusbyMovieAppComponent
import me.androidbox.busbymovies.di.TestAndroidModule
import me.androidbox.busbymovies.di.TestBusbyMovieAppComponent
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

/**
 * Created by steve on 11/22/17.
 */

@Config(sdk = intArrayOf(Build.VERSION_CODES.LOLLIPOP),
        constants = BuildConfig::class,
        packageName = "me.androidbox.busbymovies",
        application = BusbyMoviesMainApplication::class)
@RunWith(RobolectricTestRunner::class)
abstract class BaseRobolectricTestRunner {
    fun getTestBusbyMovieAppComponent(): TestBusbyMovieAppComponent =
            DaggerTestBusbyMovieAppComponent
                    .builder()
                    .testAndroidModule(TestAndroidModule())
                    .build()
}
