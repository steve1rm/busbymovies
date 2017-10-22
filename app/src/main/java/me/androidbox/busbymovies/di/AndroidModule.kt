package me.androidbox.busbymovies.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources
import android.preference.PreferenceManager
import dagger.Module
import dagger.Provides
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

    @Singleton
    @Provides
    fun providesContext(): Context = context

    @Singleton
    @Provides
    fun providesResources(): Resources = context.resources

    @Singleton
    @Provides
    fun providesSharedPreferences(): SharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(this.context)


}