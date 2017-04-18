package me.androidbox.busbymovies.support;

import org.robolectric.RuntimeEnvironment;

/**
 * Created by steve on 3/28/17.
 */

public final class ResourceLocator {
    private ResourceLocator() {}

    public static String getString(int resId) {
        return RuntimeEnvironment.application.getString(resId);
    }
}
