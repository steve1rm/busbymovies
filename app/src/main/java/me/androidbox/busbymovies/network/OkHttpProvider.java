package me.androidbox.busbymovies.network;

import okhttp3.OkHttpClient;

/**
 * Created by steve on 3/1/17.
 */

public class OkHttpProvider {
    private volatile static OkHttpClient okHttpClient;

    private OkHttpProvider() {}

    public static OkHttpClient getOkHttpClientInstance() {
        /* Single check */
        if(okHttpClient == null) {
            synchronized(OkHttpProvider.class) {
                /* Double check */
                if(okHttpClient == null) {
                    okHttpClient = new OkHttpClient();
                }
            }
        }

        return okHttpClient;
    }
}
