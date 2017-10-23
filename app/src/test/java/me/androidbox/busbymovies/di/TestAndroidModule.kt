package me.androidbox.busbymovies.di

import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import me.androidbox.busbymovies.utils.MovieSchedulers
import javax.inject.Singleton

/**
 * Created by steve on 10/23/17.
 */
class TestAndroidModule {
    @Singleton
    @Provides
    fun providesAndroidSchedulers(): MovieSchedulers {
        return object: MovieSchedulers {
            override fun getBackgroundScheduler(): Scheduler {
                return Schedulers.trampoline()
            }

            override fun getUIScheduler(): Scheduler {
                return Schedulers.trampoline()
            }
        }
    }

/*
    fun providesRecipeSchedulers(): RecipeSchedulers {
        return object : RecipeSchedulers() {
            val backgroundScheduler: Scheduler
                get() = Schedulers.trampoline()

            val uiScheduler: Scheduler
                get() = Schedulers.trampoline()
        }
    }
*/
}