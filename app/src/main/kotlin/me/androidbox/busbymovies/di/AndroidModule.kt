package me.androidbox.busbymovies.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources
import android.net.ConnectivityManager
import android.preference.PreferenceManager
import dagger.Module
import dagger.Provides
import dagger.Reusable
import me.androidbox.busbymovies.moviedetails.ImageLoaderImp
import me.androidbox.busbymovies.utils.*
import javax.inject.Singleton

/**
 * Created by steve on 10/22/17.
 */
@Module
class AndroidModule(val application: Application) {
    private val context: Context

    init {
        context = application
    }

    @Reusable
    @Provides
    fun providesContext(): Context = context

    @Reusable
    @Provides
    fun providesResources(): Resources = context.resources

    @Reusable
    @Provides
    fun providesSharedPreferences(): SharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(this.context)

    @Singleton
    @Provides
    fun providesMovieSchedulers(): MovieSchedulers = MovieSchedulersImp()

    @Singleton
    @Provides
    fun providesImageLoader(): ImageLoader = ImageLoaderImp()

    @Singleton
    @Provides
    fun providesIConnectivityProvider(context: Context?): IConnectivityProvider {
        val connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager

        return Network(connectivityManager)
    }
}