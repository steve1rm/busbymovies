package me.androidbox.busbymovies.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import me.androidbox.busbymovies.di.BusbyMoviesApplication;

/**
 * Created by steve on 2/21/17.
 */

public final class Network {
    public static boolean isConnectedToNetwork() {
        final ConnectivityManager connectivityManager =
                (ConnectivityManager)BusbyMoviesApplication.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnected();
    }
}
