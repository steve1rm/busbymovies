package me.androidbox.busbymovies.utils

import androidx.annotation.Nullable

/**
 * Created by smason on 11/2/17.
 */
interface IConnectivityProvider {
    fun isConnected(): Boolean
    fun isOnline(process: Process): Boolean
    @Nullable fun getType(): TYPE

    enum class TYPE {
        MOBILE,
        WIFI,
        OTHER
    }
}
