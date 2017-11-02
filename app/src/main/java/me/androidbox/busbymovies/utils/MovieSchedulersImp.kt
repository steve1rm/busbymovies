package me.androidbox.busbymovies.utils

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


/**
 * Created by steve on 10/22/17.
 */
class MovieSchedulersImp : MovieSchedulers {
    override fun getBackgroundScheduler(): Scheduler =
            Schedulers.io()

    override fun getUIScheduler(): Scheduler =
            AndroidSchedulers.mainThread()
}
