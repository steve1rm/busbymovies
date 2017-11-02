package me.androidbox.busbymovies.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;

import com.google.common.base.Preconditions;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import me.androidbox.busbymovies.di.BusbyMoviesApplication;
import timber.log.Timber;

/**
 * Created by steve on 2/21/17.
 */

public final class Network implements IConnectivityProvider {
    public static boolean isConnectedToNetwork() {
        final ConnectivityManager connectivityManager =
                (ConnectivityManager)BusbyMoviesApplication.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnected();
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

    private @NonNull final ConnectivityManager connectivityManager;

    public Network(@NonNull final ConnectivityManager connectivityManager) {
        this.connectivityManager = Preconditions.checkNotNull(connectivityManager);
    }

    @Override
    public boolean isConnected() {
        final NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnected();
    }

    @NotNull
    @Override
    public TYPE getType() {
        return null;
    }
}
