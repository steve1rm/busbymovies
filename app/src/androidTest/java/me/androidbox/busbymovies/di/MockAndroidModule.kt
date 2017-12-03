package me.androidbox.busbymovies.di

import android.content.Context
import dagger.Module
import dagger.Provides
import me.androidbox.busbymovies.moviedetails.ImageLoaderImp
import me.androidbox.busbymovies.utils.ImageLoader
import me.androidbox.busbymovies.utils.MovieSchedulers
import me.androidbox.busbymovies.utils.MovieSchedulersImp
import org.mockito.Mockito.mock
import javax.inject.Singleton

/**
 * Created by steve on 12/2/17.
 */
@Module
class MockAndroidModule {
    @Singleton
    @Provides
    fun providesContext(): Context = mock(Context::class.java)

    @Singleton
    @Provides
    fun providesMovieSchedulers(): MovieSchedulers {
        return MovieSchedulersImp()
    }

    @Singleton
    @Provides
    fun providesImageLoader(): ImageLoader = mock(ImageLoaderImp::class.java)
}