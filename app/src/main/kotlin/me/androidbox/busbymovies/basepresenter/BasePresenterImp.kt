package me.androidbox.busbymovies.basepresenter

import android.support.annotation.UiThread
import java.lang.ref.WeakReference

/**
 * Created by steve on 12/24/17.
 */
open class BasePresenterImp<V> : BasePresenter<V> {
    private var viewReference: WeakReference<V>? = null

    @UiThread
    override fun attachView(view: V) {
        viewReference = WeakReference(view)
    }

    @UiThread
    override fun detachView() {
        if (viewReference != null) {
            viewReference?.clear()
            viewReference = null
        }
    }

    @UiThread
    override fun isViewAttached(): Boolean {
        return (viewReference != null && viewReference?.get() != null)
    }

    @UiThread
    override fun getView(): V? {
        return if (isViewAttached()) return viewReference?.get() else null
    }
}
