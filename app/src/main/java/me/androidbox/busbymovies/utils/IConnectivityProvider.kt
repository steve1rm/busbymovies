package me.androidbox.busbymovies.utils

/**
 * Created by smason on 11/2/17.
 */
interface IConnectivityProvider {
    fun isConnected(): Boolean
    fun getType(): TYPE

    enum class TYPE {
        MOBILE,
        WIFI,
        OTHER
    }
}
