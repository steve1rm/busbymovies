package me.androidbox.busbymovies.di

import dagger.Component
import javax.inject.Singleton

/**
 * Created by steve on 10/22/17.
 */
@Singleton
@Component(modules = arrayOf(ApiModule::class))
interface AppComponent {

}