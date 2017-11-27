package me.androidbox.busbymovies.utils

import io.reactivex.Scheduler

/**
 * Created by steve on 10/22/17.
 */
interface MovieSchedulers {
    fun getBackgroundScheduler(): Scheduler
    fun getUIScheduler(): Scheduler
}