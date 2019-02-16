package me.androidbox.busbymovies.utils;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import timber.log.Timber;

/**
 * Created by steve on 2/21/17.
 */

public final class Network implements IConnectivityProvider {
    private @NonNull final ConnectivityManager connectivityManager;

    public Network(@NonNull final ConnectivityManager connectivityManager) {
         // this.connectivityManager = Preconditions.checkNotNull(connectivityManager);
         this.connectivityManager = connectivityManager;
    }

    @Override
    public boolean isConnected() {
        final NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnected();
    }

    @Nullable
    @Override
    public TYPE getType() {
        final NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if(networkInfo == null) {
            return null;
        }
        else {
            final TYPE type;

            switch(networkInfo.getType()) {
                case ConnectivityManager.TYPE_MOBILE: {
                    type = TYPE.MOBILE;
                }
                break;

                case ConnectivityManager.TYPE_WIFI: {
                    type = TYPE.WIFI;
                }
                break;

                default: {
                    type = TYPE.OTHER;
                }
                break;
            }

            return type;
        }
    }


    /**
     *
     * @param process Runtime.getRuntime().exec("/system/bin/ping -c 8.8.8.8")
     * @return true of false if online
     */
    @Override
    public boolean isOnline(final Process process) {
        boolean hasConnected = false;

        try {
            final int exitValue = process.waitFor();

            hasConnected = (exitValue == 0 || exitValue == 2);
        }
        catch(InterruptedException e) {
            Timber.e(e.getMessage());
        }

        return hasConnected;
    }
}

