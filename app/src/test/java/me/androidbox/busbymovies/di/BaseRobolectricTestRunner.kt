package me.androidbox.busbymovies.di

import android.os.Build
import me.androidbox.busbymovies.BuildConfig
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

/**
 * Created by steve on 11/19/17.
 */
@Config(constants = BuildConfig::class,
        packageName = "me.androidbox.busbymovies",
        application = BusbyMoviesMainApplication::class,
        sdk = intArrayOf(Build.VERSION_CODES.LOLLIPOP))
@RunWith(RobolectricTestRunner::class)
class BaseRobolectricTestRunner {

    fun getTestBusbyMovieAppComponent(): TestBusbyMovieAppComponent =
            DaggerTestBusbyMovieAppComponent
                    .builder()
                    .testAndroidModule(TestAndroidModule())
                    .build()
}