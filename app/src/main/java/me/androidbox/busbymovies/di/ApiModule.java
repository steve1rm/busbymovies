package me.androidbox.busbymovies.di;

import android.app.Application;

import java.io.File;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import me.androidbox.busbymovies.BuildConfig;
import me.androidbox.busbymovies.network.MovieAPIService;
import me.androidbox.busbymovies.utils.Constants;
import me.androidbox.busbymovies.utils.IConnectivityProvider;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

/**
 * Created by steve on 2/18/17.
 */
@Module
public class ApiModule {
    private static final String CACHE_CONTROL = "Cache-Control";
    private Application application;

    public ApiModule(final Application application) {
        this.application = application;
    }

    @Singleton
    @Provides
    public OkHttpClient provideLoggingCapableHttpClient(final IConnectivityProvider connectivityProvider) {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();

        /* Only print debug output in debug mode */
        loggingInterceptor.setLevel(BuildConfig.DEBUG ?
                HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);

        return new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addNetworkInterceptor(provideCacheInterceptor())
                .addInterceptor(provideOfflineCacheInterceptor(connectivityProvider))
                .cache(provideCache())
                .build();
    }

    public Retrofit provideRetrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    public MovieAPIService provideMovieService(OkHttpClient okHttpClient) {
        return provideRetrofit(okHttpClient).create(MovieAPIService.class);
    }

    /* Create and stores cache on the device for up to 10MB */
    private Cache provideCache() {
        Cache cache = null;

        try {
            cache = new Cache(
                    new File(this.application.getCacheDir(), "http-cache"),
                    10 * 1024 * 1024); /* 10MB */
        }
        catch(Exception ex) {
            Timber.e(ex, "Could not create Cache!");
        }

        return cache;
    }

    /* Provides a cache that will prevent network calls from within 2 minutes of each other */
    private static Interceptor provideCacheInterceptor() {
        return chain -> {
            final Response response = chain.proceed(chain.request());

            /* Re-write response header to force use of cache */
            final CacheControl cacheControl = new CacheControl.Builder()
                    .maxAge(2, TimeUnit.MINUTES)
                    .build();

            return response.newBuilder()
                    .header(CACHE_CONTROL, cacheControl.toString())
                    .build();
        };
    }

    /* Will use the offline cache that is not older than 7 days to be used for when the device is offline */
    public Interceptor provideOfflineCacheInterceptor(final IConnectivityProvider connectivityProvider) {
        return chain -> {
            Request request = chain.request();

            /* if not connected to the network use the offline cache */
            if(!connectivityProvider.isConnected()) {
                final CacheControl cacheControl = new CacheControl.Builder()
                        .maxStale(7, TimeUnit.DAYS)
                        .build();

                request = request.newBuilder()
                        .cacheControl(cacheControl)
                        .build();
            }

            try {
                return chain.proceed(request);
            }
            catch(Exception e) {
                Timber.e(e, e.getMessage());
                return null;
            }
        };
    }
}
