package me.androidbox.busbymovies.utils

import android.support.annotation.Nullable

/**
 * Created by smason on 11/2/17.
 */
interface IConnectivityProvider {
    fun isConnected(): Boolean
    @Nullable fun getType(): TYPE

    enum class TYPE {
        MOBILE,
        WIFI,
        OTHER
    }
}
