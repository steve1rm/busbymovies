package me.androidbox.busbymovies.utils

import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

/**
 * Created by steve on 12/8/17.
 */
class TestMovieSchedulersImp : MovieSchedulers {
    override fun getBackgroundScheduler(): Scheduler {
        return Schedulers.trampoline()
    }

    override fun getUIScheduler(): Scheduler {
        return Schedulers.trampoline()
    }
}