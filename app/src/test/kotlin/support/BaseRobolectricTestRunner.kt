package support

import android.os.Build
import android.support.v4.app.Fragment
import di.DaggerTestBusbyMoviesAppComponent
import di.TestAndroidModule
import di.TestBusbyMoviesAppComponent
import di.TestMovieListModule
import me.androidbox.busbymovies.BuildConfig
import me.androidbox.busbymovies.di.BusbyMoviesMainApplication
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.shadows.support.v4.SupportFragmentTestUtil

/**
 * Created by steve on 11/22/17.
 */

@Config(sdk = intArrayOf(Build.VERSION_CODES.LOLLIPOP),
        constants = BuildConfig::class,
        packageName = "me.androidbox.busbymovies",
        application = BusbyMoviesMainApplication::class)
@RunWith(RobolectricTestRunner::class)
abstract class BaseRobolectricTestRunner {

    protected fun initializeFragment(fragment: Fragment) {
        SupportFragmentTestUtil.startVisibleFragment(fragment)
    }

    protected fun getTestBusbyMovieAppComponent(): TestBusbyMoviesAppComponent =
            DaggerTestBusbyMoviesAppComponent
                    .builder()
                    .testAndroidModule(TestAndroidModule())
                    .testMovieListModule(TestMovieListModule())
                    .build()
}
