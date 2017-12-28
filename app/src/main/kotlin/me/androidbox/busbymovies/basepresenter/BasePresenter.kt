package me.androidbox.busbymovies.basepresenter

/**
 * Created by steve on 12/24/17.
 */
interface BasePresenter<V> {
    fun attachView(view: V)
    fun detachView()
    fun isViewAttached(): Boolean
    fun getView(): V?
}
