package me.androidbox.busbymovies.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.IOException;

import me.androidbox.busbymovies.di.BusbyMoviesMainApplication;
import timber.log.Timber;

/**
 * Created by steve on 2/21/17.
 */

public final class Network {
    public static boolean isConnectedToNetwork() {
        final ConnectivityManager connectivityManager =
                (ConnectivityManager) BusbyMoviesMainApplication.getBusbyInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

    public static boolean isOnline() {
        final Runtime runtime = Runtime.getRuntime();
        boolean hasConnected = false;

        try {
            final Process ipProcess = runtime.exec("/system/bin/ping -c 8.8.8.8");
            final int exitValue = ipProcess.waitFor();

            hasConnected = (exitValue == 0 || exitValue == 2);
        }
        catch(InterruptedException | IOException e) {
            Timber.e(e.getMessage());
        }

        return hasConnected;
    }
}
